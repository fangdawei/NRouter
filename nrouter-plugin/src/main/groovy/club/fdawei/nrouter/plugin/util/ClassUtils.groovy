package club.fdawei.nrouter.plugin.util

/**
 * Created by david on 2019/05/29.
 */
class ClassUtils {

    static String getClassName(File dir, File classFile) {
        def dirPath = dir.absolutePath
        if (!dirPath.endsWith(File.separator)) {
            dirPath = dirPath.concat(File.separator)
        }
        def relativePath = classFile.absolutePath.replace(dirPath, '')
        return getClassName(relativePath)
    }

    static String getClassName(String path) {
        if (!path.endsWith('.class')) {
            return null
        }
        return path.replace('.class', '')
                .replace(File.separator, '.')
    }
}
