package club.fdawei.nrouter.plugin.util

import com.android.utils.FileUtils

/**
 * Created by david on 2019/05/29.
 */
class ClassUtils {

    static String getClassName(File dir, File classFile) {
        def relativePath = FileUtils.relativePath(classFile, dir)
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
