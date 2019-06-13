package club.fdawei.mourouter.plugin

import club.fdawei.mourouter.plugin.common.KaptArgKeys
import com.android.build.gradle.AppExtension
import com.android.build.gradle.AppPlugin
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Created by david on 2019/05/27.
 */
class MouRouterPlugin implements Plugin<Project> {

    private static final String TAG = "MouRouterPlugin"

    @Override
    void apply(Project target) {
        def isApp = target.plugins.hasPlugin(AppPlugin)
        def kapt = target.extensions.findByName('kapt')
        if (kapt != null) {
            kapt.arguments {
                arg(KaptArgKeys.ARG_MODULE_NAME, target.name)
                arg(KaptArgKeys.ARG_IS_APP, isApp)
            }
        }
        if (isApp) {
            def android = target.extensions.findByType(AppExtension)
            if (android != null) {
                android.registerTransform(new MouRouterTransform())
            }
        }
    }
}
