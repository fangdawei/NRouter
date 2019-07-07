package club.fdawei.nrouter.plugin.handler

import club.fdawei.nrouter.plugin.base.ProviderInfo
import club.fdawei.nrouter.plugin.common.ClassInfo
import club.fdawei.nrouter.plugin.log.PluginLogger
import club.fdawei.nrouter.plugin.util.ClassUtils
import com.android.build.api.transform.Format
import com.android.build.api.transform.QualifiedContent
import com.android.build.api.transform.TransformInvocation
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
    private List<ProviderInfo> providerList = new LinkedList<>()

    TransformHandler(TransformInvocation invocation) {
        this.invocation = invocation
    }

    void transform() {
        invocation.inputs.each {
            it.directoryInputs.each { dir ->
                PluginLogger.i("transform", "${dir.scopes}, ${dir.file.absolutePath}, ${dir.contentTypes}")
                classPool.appendClassPath(dir.file.absolutePath)
                collectInDir(dir.file)

                if (projectClassesDir == null && dir.scopes.contains(QualifiedContent.Scope.PROJECT)) {
                    projectClassesDir = dir.file
                }
            }
            it.jarInputs.each { jar ->
                PluginLogger.i("transform", "${jar.scopes}, ${jar.file.absolutePath}, ${jar.contentTypes}")
                classPool.appendClassPath(jar.file.absolutePath)
                collectInJar(jar.file)
            }
        }

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
                def clazz = classPool.getCtClass(it.name)
                def interfaces = clazz.interfaces
                if (interfaces != null && interfaces.contains(multiProvider)) {
                    methodSrcBuilder.append("${ClassInfo.AbsAppProvider.METHOD_ADD_PROVIDER}(new ${it.name}());")
                }
            }
            methodSrcBuilder.append('}')
            appProvider.addMethod(CtNewMethod.make(methodSrcBuilder.toString(), appProvider))
            appProvider.writeFile(projectClassesDir.absolutePath)

            PluginLogger.i("", "create class ${ClassInfo.MultiProvider.NAME}")
        }

        invocation.inputs.each {
            it.directoryInputs.each { dir ->
                def location = invocation.outputProvider.getContentLocation(
                        dir.name, dir.contentTypes, dir.scopes, Format.DIRECTORY)
                FileUtils.copyDirectory(dir.file, location)
            }
            it.jarInputs.each { jar ->
                def location = invocation.outputProvider.getContentLocation(
                        jar.name, jar.contentTypes, jar.scopes, Format.JAR)
                FileUtils.copyFile(jar.file, location)
            }
        }
    }

    private void collectInDir(File dir) {
        dir.eachFileRecurse {
            if (!it.directory) {
                def relativePath = FileUtils.relativePath(it, dir)
                def className = ClassUtils.getClassName(relativePath)
                if (ClassInfo.ModuleProvider.isModuleProvider(relativePath)) {
                    PluginLogger.i("", "${className}")
                    providerList.add(new ProviderInfo(dir, className))
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
            if (ClassInfo.ModuleProvider.isModuleProvider(jarEntry.name)) {
                def className = ClassUtils.getClassName(jarEntry.name)
                PluginLogger.i("", "${className}")
                providerList.add(new ProviderInfo(jar, className))
            }
        }
    }
}
