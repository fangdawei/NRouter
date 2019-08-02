package club.fdawei.nrouter.plugin.handler

import club.fdawei.nrouter.plugin.base.IHandler
import club.fdawei.nrouter.plugin.common.ClassInfo
import club.fdawei.nrouter.plugin.common.LogTag
import club.fdawei.nrouter.plugin.log.PluginLogger
import club.fdawei.nrouter.plugin.util.ClassUtils
import com.android.build.api.transform.*
import com.android.ide.common.internal.WaitableExecutor
import com.android.utils.FileUtils
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.stream.JsonWriter
import javassist.ClassPool
import javassist.CtNewMethod
import org.apache.commons.codec.digest.DigestUtils
import org.gradle.api.Project

import java.util.jar.JarFile

/**
 * Created by david on 2019/07/24.
 */
class ProviderCollector implements IHandler {

    private static final String TAG = 'ProviderCollector'

    private Project project
    private TransformInvocation invocation
    private final ProviderContainer providerContainer = new ProviderContainer()
    private ClassPool classPool = new ClassPool(true)

    private ProviderCollector() {

    }

    @Override
    void handle() {
        PluginLogger.i(LogTag.COMMON, "collect provider, incremental=${invocation.incremental}")
        long startTime = System.currentTimeMillis()
        if (invocation.incremental && providerContainer.loadFromCache()) {
            incrementalProcess()
        } else {
            invocation.outputProvider.deleteAll()
            fullProcess()
        }
        long costTime = System.currentTimeMillis() - startTime
        PluginLogger.i(LogTag.COMMON, "collect provider finish, cost time " +
                "${String.format('%.3f', costTime / 1000f)}s")
    }

    private void fullProcess() {
        File classWriteDir = null
        final def collectExecutor = WaitableExecutor.useGlobalSharedThreadPool()
        invocation.inputs.each {
            it.directoryInputs.each { dir ->
                classPool.appendClassPath(dir.file.absolutePath)
                def location = invocation.outputProvider.getContentLocation(
                        dir.name, dir.contentTypes, dir.scopes, Format.DIRECTORY)
                if (classWriteDir == null && dir.scopes.contains(QualifiedContent.Scope.PROJECT)) {
                    classWriteDir = location
                    def inputKey = parseInputKey(dir, Format.DIRECTORY)
                    providerContainer.classWriteKey = inputKey
                }
                collectExecutor.execute {
                    collectInDir(dir)
                    FileUtils.copyDirectory(dir.file, location)
                }
            }
            it.jarInputs.each { jar ->
                classPool.appendClassPath(jar.file.absolutePath)
                def location = invocation.outputProvider.getContentLocation(
                        jar.name, jar.contentTypes, jar.scopes, Format.JAR)
                collectExecutor.execute {
                    collectInJar(jar)
                    FileUtils.copyFile(jar.file, location)
                }
            }
        }
        collectExecutor.waitForTasksWithQuickFail(true)
        generateAppProvider(classWriteDir)
        providerContainer.saveToCache()
    }

    private void collectInDir(DirectoryInput input) {
        final String inputKey = parseInputKey(input, Format.DIRECTORY)
        input.file.eachFileRecurse {
            if (!it.directory) {
                def className = ClassUtils.getClassName(input.file, it)
                if (ClassInfo.ModuleProvider.isModuleProvider(className)) {
                    onFindProvider(inputKey, ProviderInfo.fromDir(input.file.absolutePath, className))
                }
            }
        }
    }

    private void collectInJar(JarInput input) {
        final String inputKey = parseInputKey(input, Format.JAR)
        def jarFile = new JarFile(input.file)
        def entries = jarFile.entries()
        while (entries.hasMoreElements()) {
            def jarEntry = entries.nextElement()
            if (jarEntry.directory) {
                continue
            }
            def className = ClassUtils.getClassName(jarEntry.name)
            if (ClassInfo.ModuleProvider.isModuleProvider(className)) {
                onFindProvider(inputKey, ProviderInfo.fromJar(input.file.absolutePath, className))
            }
        }
    }

    private void incrementalProcess() {
        File firstValidClassWriteDir = null
        String firstValidClassWriteKey = null
        File lastClassWriteDir = null
        invocation.inputs.each {
            it.directoryInputs.each { dir ->
                def inputKey = parseInputKey(dir, Format.JAR)
                def location = invocation.outputProvider.getContentLocation(
                        dir.name, dir.contentTypes, dir.scopes, Format.DIRECTORY)
                if (dir.file != null && dir.file.exists()) {
                    classPool.appendClassPath(dir.file.absolutePath)
                    dir.changedFiles.each { changedFiles ->
                        changedFiles.each { entry ->
                            def destFile = new File(entry.key.absolutePath.replace(dir.file.absolutePath,
                                    location.absolutePath))
                            def className = ClassUtils.getClassName(dir.file, entry.key)
                            switch (entry.value) {
                                case Status.NOTCHANGED:
                                    break
                                case Status.ADDED:
                                    if (ClassInfo.ModuleProvider.isModuleProvider(className)) {
                                        providerContainer.addProvider(inputKey,
                                                ProviderInfo.fromDir(dir.file.absolutePath, className))
                                    }
                                    FileUtils.copyFile(entry.key, destFile)
                                    break
                                case Status.CHANGED:
                                    FileUtils.deleteIfExists(destFile)
                                    FileUtils.copyFile(entry.key, destFile)
                                    break
                                case Status.REMOVED:
                                    providerContainer.removeProvider(inputKey, className)
                                    FileUtils.deleteIfExists(destFile)
                                    break
                            }
                        }
                    }
                    if (inputKey == providerContainer.classWriteKey) {
                        lastClassWriteDir = location
                    }
                    if (firstValidClassWriteDir == null && dir.scopes.contains(QualifiedContent.Scope.PROJECT)) {
                        firstValidClassWriteDir = location
                        firstValidClassWriteKey = inputKey
                    }
                } else {
                    providerContainer.remove(inputKey)
                    FileUtils.deleteIfExists(location)
                }
            }
            it.jarInputs.each { jar ->
                def inputKey = parseInputKey(jar, Format.JAR)
                def location = invocation.outputProvider.getContentLocation(
                        jar.name, jar.contentTypes, jar.scopes, Format.JAR)
                switch (jar.status) {
                    case Status.NOTCHANGED:
                        break
                    case Status.ADDED:
                    case Status.CHANGED:
                        providerContainer.remove(inputKey)
                        collectInJar(jar)
                        FileUtils.deleteIfExists(location)
                        FileUtils.copyFile(jar.file, location)
                        break
                    case Status.REMOVED:
                        providerContainer.remove(inputKey)
                        FileUtils.deleteIfExists(location)
                        break
                }
                if (jar.status != Status.REMOVED) {
                    classPool.appendClassPath(jar.file.absolutePath)
                }
            }
        }
        if (lastClassWriteDir != null) {
            generateAppProvider(lastClassWriteDir)
        } else {
            generateAppProvider(firstValidClassWriteDir)
            providerContainer.classWriteKey = firstValidClassWriteKey
        }
        providerContainer.saveToCache()
    }

    private void generateAppProvider(File writeDir) {
        if (writeDir != null) {
            def multiProvider = classPool.getCtClass(ClassInfo.MultiProvider.NAME)
            def absAppProvider = classPool.getCtClass(ClassInfo.AbsAppProvider.NAME)
            def appProvider = classPool.makeClass(ClassInfo.AppProvider.NAME, absAppProvider)
            if (appProvider.frozen) {
                appProvider.defrost()
            }
            def methodSrcBuilder = new StringBuilder("public final void " +
                    "${ClassInfo.AbsAppProvider.METHOD_INIT_PROVIDERS}()")
            methodSrcBuilder.append('{')
            providerContainer.providers.each {
                PluginLogger.i(LogTag.COMMON, "find ${it.className} in ${it.fromTypeName}(${it.fromFile})")
                def clazz = classPool.getCtClass(it.className)
                def interfaces = clazz.interfaces
                if (interfaces != null && interfaces.contains(multiProvider)) {
                    methodSrcBuilder.append("${ClassInfo.AbsAppProvider.METHOD_ADD_PROVIDER}(new ${it.className}());")
                }
            }
            methodSrcBuilder.append('}')
            appProvider.addMethod(CtNewMethod.make(methodSrcBuilder.toString(), appProvider))
            appProvider.writeFile(writeDir.absolutePath)
            PluginLogger.i(LogTag.COMMON, "generate class ${ClassInfo.AppProvider.NAME}")
        } else {
            PluginLogger.e(LogTag.COMMON, "class ${ClassInfo.AppProvider.NAME} write dir is null")
        }
    }

    private void onFindProvider(String fromKey, ProviderInfo provider) {
        synchronized (providerContainer) {
            providerContainer.addProvider(fromKey, provider)
        }
    }

    static ProviderCollector of(Project project, TransformInvocation invocation) {
        def collector = new ProviderCollector()
        collector.project = project
        collector.invocation = invocation
        def incrementalDir = new File(project.buildDir,
                "intermediates/incremental/collect${invocation.context.variantName.capitalize()}Providers/")
        collector.providerContainer.cacheDir = incrementalDir
        return collector
    }

    static String parseInputKey(QualifiedContent content, Format format) {
        return DigestUtils.md5Hex("${content.name}#${content.contentTypes}#${content.scopes}#${format}")
    }

    static class ProviderInfo {

        static final int FROM_DIR = 0
        static final int FROM_JAR = 1

        @SerializedName('fromType')
        private int fromType
        @SerializedName('fromFile')
        private String fromFile
        @SerializedName('className')
        private String className

        private ProviderInfo() {

        }

        int getFromType() {
            return fromType
        }

        String getFromTypeName() {
            if (fromType == FROM_JAR) {
                return 'jar'
            } else {
                return 'dir'
            }
        }

        String getFromFile() {
            return fromFile
        }

        String getClassName() {
            return className
        }

        static ProviderInfo fromJar(String fromFile, String className) {
            return create(FROM_JAR, fromFile, className)
        }

        static ProviderInfo fromDir(String fromFile, String className) {
            return create(FROM_DIR, fromFile, className)
        }

        private static ProviderInfo create(int fromType, String fromFile, String className) {
            def info = new ProviderInfo()
            info.fromType = fromType
            info.fromFile = fromFile
            info.className = className
            return info
        }
    }

    static class ProviderContainerData {
        @SerializedName('version')
        long version
        @SerializedName('data')
        Map<String, Map<String, ProviderInfo>> data
        @SerializedName('classWriteKey')
        String classWriteKey
    }

    static class ProviderContainer {

        private static final long VERSION = 1L
        private static final String CACHE_FILE = 'providers.json'

        private final Gson gson = new Gson()
        private File cacheDir
        private final Map<String, Map<String, ProviderInfo>> data = new LinkedHashMap<>()
        private String classWriteKey

        void setCacheDir(File cacheDir) {
            this.cacheDir = cacheDir
        }

        void setClassWriteKey(String classWriteKey) {
            this.classWriteKey = classWriteKey
        }

        private Map<String, ProviderInfo> getProviderMap(String fromKey) {
            def map = data.get(fromKey)
            if (map == null) {
                map = new LinkedHashMap<String, ProviderInfo>()
                data.put(fromKey, map)
            }
            return map
        }

        void addProvider(String fromKey, ProviderInfo provider) {
            getProviderMap(fromKey).put(provider.className, provider)
        }

        void removeProvider(String fromKey, String className) {
            getProviderMap(fromKey).remove(className)
        }

        void remove(String fromKey) {
            data.remove(fromKey)
        }

        boolean loadFromCache() {
            if (cacheDir == null) {
                return false
            }
            def cacheFile = new File(cacheDir, CACHE_FILE)
            if (!cacheFile.exists()) {
                return false
            }
            ProviderContainerData cacheData = null
            def cacheFileReader = new FileReader(cacheFile)
            try {
                cacheData = gson.fromJson(cacheFileReader, ProviderContainerData.class)
            } catch (Exception e) {
                FileUtils.deleteIfExists(cacheFile)
                PluginLogger.e(LogTag.COMMON, "loadFromCache error, ${e.message}")
            } finally {
                cacheFileReader.close()
            }
            if (cacheData == null || cacheData.version != VERSION || cacheData.data == null) {
                return false
            }
            data.putAll(cacheData.data)
            classWriteKey = cacheData.classWriteKey
            return true
        }

        void saveToCache() {
            if (cacheDir == null) {
                return
            }
            def cacheFile = new File(cacheDir, CACHE_FILE)
            FileUtils.deleteIfExists(cacheFile)
            FileUtils.mkdirs(cacheDir)
            JsonWriter jsonWriter = null
            try {
                jsonWriter = gson.newJsonWriter(new FileWriter(cacheFile))
                def saveData = new ProviderContainerData()
                saveData.version = VERSION
                saveData.data = data
                saveData.classWriteKey = classWriteKey
                gson.toJson(saveData, ProviderContainerData.class, jsonWriter)
                jsonWriter.flush()
            } catch (Exception e) {
                FileUtils.deleteIfExists(cacheFile)
                PluginLogger.e(LogTag.COMMON, "saveToCache error, ${e.message}")
            } finally {
                if (jsonWriter != null) {
                    jsonWriter.close()
                }
            }
        }

        List<ProviderInfo> getProviders() {
            List<ProviderInfo> providerList = new LinkedList<>()
            data.values().each {
                if (it != null) {
                    providerList.addAll(it.values())
                }
            }
            return providerList
        }
    }
}
