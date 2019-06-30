package club.fdawei.nrouter.plugin

import club.fdawei.nrouter.plugin.base.ProviderInfo
import club.fdawei.nrouter.plugin.common.ClassInfo
import club.fdawei.nrouter.plugin.util.ClassUtils
import com.android.build.api.transform.Format
import com.android.build.api.transform.TransformInvocation
import com.android.utils.FileUtils
import javassist.ClassPool

import java.util.jar.JarFile

/**
 * Created by david on 2019/05/28.
 */
class TransformHandler {

    private static final String TAG = 'TransformHandler'

    private TransformInvocation invocation
    private ClassPool classPool = new ClassPool(true)
    private ProviderInfo appProviderInfo
    private List<ProviderInfo> providerList = new LinkedList<>()

    TransformHandler(TransformInvocation invocation) {
        this.invocation = invocation
    }

    void transform() {
        invocation.inputs.each {
            it.directoryInputs.each { dir ->
                classPool.appendClassPath(dir.file.absolutePath)
                collectInDir(dir.file)
            }
            it.jarInputs.each { jar ->
                classPool.appendClassPath(jar.file.absolutePath)
                collectInJar(jar.file)
            }
        }

        if (appProviderInfo != null && !providerList.empty) {
            def appProvider = classPool.getCtClass(appProviderInfo.name)
            if (appProvider.frozen) {
                appProvider.defrost()
            }
            def initProvidersMethod = appProvider.getDeclaredMethod(ClassInfo.AbsAppProvider.METHOD_INIT_PROVIDERS)
            def routeProvider = classPool.getCtClass(ClassInfo.MultiProvider.NAME)
            def srcBuilder = new StringBuilder()
            srcBuilder.append('{')
            providerList.each {
                def clazz = classPool.getCtClass(it.name)
                def interfaces = clazz.interfaces
                if (interfaces != null && interfaces.contains(routeProvider)) {
                    srcBuilder.append("${ClassInfo.AbsAppProvider.METHOD_ADD_PROVIDER}(new ${it.name}());")
                }
            }
            srcBuilder.append('}')
            initProvidersMethod.setBody(srcBuilder.toString())
            appProvider.writeFile(appProviderInfo.dir.absolutePath)
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
                    providerList.add(new ProviderInfo(dir, className))
                } else if (ClassInfo.AppProvider.NAME == className) {
                    appProviderInfo = new ProviderInfo(dir, className)
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
                providerList.add(new ProviderInfo(jar, className))
            }
        }
    }
}
