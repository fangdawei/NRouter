package club.fdawei.nrouter.plugin.handler

import club.fdawei.nrouter.plugin.base.ProviderInfo
import club.fdawei.nrouter.plugin.common.ClassInfo
import club.fdawei.nrouter.plugin.common.LogTag
import club.fdawei.nrouter.plugin.log.PluginLogger
import club.fdawei.nrouter.plugin.util.ClassUtils
import com.android.build.api.transform.Format
import com.android.build.api.transform.QualifiedContent
import com.android.build.api.transform.TransformInvocation
import com.android.ide.common.internal.WaitableExecutor
import com.android.utils.FileUtils
import javassist.ClassPool
import javassist.CtNewMethod

import java.util.jar.JarFile

/**
 * Created by david on 2019/05/28.
 */
class TransformHandler {

    private static final String TAG = 'TransformHandler'

    private TransformInvocation invocation
    private ClassPool classPool = new ClassPool(true)
    private File projectClassesDir = null
    private final List<ProviderInfo> providerList = new LinkedList()

    TransformHandler(TransformInvocation invocation) {
        this.invocation = invocation
    }

    void transform() {
        final def collectExecutor = WaitableExecutor.useGlobalSharedThreadPool()
        invocation.inputs.each {
            it.directoryInputs.each { dir ->
                classPool.appendClassPath(dir.file.absolutePath)
                if (projectClassesDir == null && dir.scopes.contains(QualifiedContent.Scope.PROJECT)) {
                    projectClassesDir = dir.file
                }
                collectExecutor.execute {
                    collectInDir(dir.file)
                }
            }
            it.jarInputs.each { jar ->
                classPool.appendClassPath(jar.file.absolutePath)
                collectExecutor.execute {
                    collectInJar(jar.file)
                }
            }
        }
        collectExecutor.waitForTasksWithQuickFail(true)

        if (projectClassesDir != null) {
            def multiProvider = classPool.getCtClass(ClassInfo.MultiProvider.NAME)
            def absAppProvider = classPool.getCtClass(ClassInfo.AbsAppProvider.NAME)
            def appProvider = classPool.makeClass(ClassInfo.AppProvider.NAME, absAppProvider)
            if (appProvider.frozen) {
                appProvider.defrost()
            }
            def methodSrcBuilder = new StringBuilder("public final void " +
                    "${ClassInfo.AbsAppProvider.METHOD_INIT_PROVIDERS}()")
            methodSrcBuilder.append('{')
            providerList.each {
                PluginLogger.i(LogTag.COMMON, "find ${it.name} in ${it.from.value}(${it.fromFile.absolutePath})")
                def clazz = classPool.getCtClass(it.name)
                def interfaces = clazz.interfaces
                if (interfaces != null && interfaces.contains(multiProvider)) {
                    methodSrcBuilder.append("${ClassInfo.AbsAppProvider.METHOD_ADD_PROVIDER}(new ${it.name}());")
                }
            }
            methodSrcBuilder.append('}')
            appProvider.addMethod(CtNewMethod.make(methodSrcBuilder.toString(), appProvider))
            appProvider.writeFile(projectClassesDir.absolutePath)
            PluginLogger.i(LogTag.COMMON, "generate class ${ClassInfo.AppProvider.NAME}")
        } else {
            PluginLogger.e(LogTag.COMMON, 'projectClassesDir not found!')
        }

        final def copyExecutor = WaitableExecutor.useGlobalSharedThreadPool()
        invocation.inputs.each {
            it.directoryInputs.each { dir ->
                def location = invocation.outputProvider.getContentLocation(
                        dir.name, dir.contentTypes, dir.scopes, Format.DIRECTORY)
                copyExecutor.execute {
                    FileUtils.copyDirectory(dir.file, location)
                }
            }
            it.jarInputs.each { jar ->
                def location = invocation.outputProvider.getContentLocation(
                        jar.name, jar.contentTypes, jar.scopes, Format.JAR)
                copyExecutor.execute {
                    FileUtils.copyFile(jar.file, location)
                }
            }
        }
        copyExecutor.waitForTasksWithQuickFail(true)
    }

    private void addProviderInfo(ProviderInfo provider) {
        synchronized (providerList) {
            providerList.add(provider)
        }
    }

    private void collectInDir(File dir) {
        dir.eachFileRecurse {
            if (!it.directory) {
                def className = ClassUtils.getClassName(dir, it)
                if (ClassInfo.ModuleProvider.isModuleProvider(className)) {
                    addProviderInfo(ProviderInfo.fromDir(dir, className))
                }
            }
        }
    }

    private void collectInJar(File jar) {
        def jarFile = new JarFile(jar)
        def entries = jarFile.entries()
        while (entries.hasMoreElements()) {
            def jarEntry = entries.nextElement()
            if (jarEntry.directory) {
                continue
            }
            def className = ClassUtils.getClassName(jarEntry.name)
            if (ClassInfo.ModuleProvider.isModuleProvider(className)) {
                addProviderInfo(ProviderInfo.fromJar(jar, className))
            }
        }
    }
}
