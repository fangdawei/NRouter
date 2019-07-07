package club.fdawei.nrouter.plugin

import club.fdawei.nrouter.plugin.common.KaptArgKeys
import club.fdawei.nrouter.plugin.ext.NRouterExtension
import club.fdawei.nrouter.plugin.handler.ManifestHandler
import com.android.build.gradle.AppExtension
import com.android.build.gradle.AppPlugin
import com.android.build.gradle.api.ApplicationVariant
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Created by david on 2019/05/27.
 */
class NRouterPlugin implements Plugin<Project> {

    private static final String TAG = "NRouterPlugin"

    @Override
    void apply(Project target) {
        target.extensions.create('nrouter', NRouterExtension)

        // config kapt
        def kapt = target.extensions.findByName('kapt')
        if (kapt != null) {
            kapt.arguments {
                arg(KaptArgKeys.ARG_MODULE_NAME, target.name)
                arg(KaptArgKeys.ARG_IS_APP, target.plugins.hasPlugin(AppPlugin))
            }
        }

        def android = target.extensions.findByType(AppExtension)
        if (android != null) {
            android.registerTransform(new NRouterTransform())
        }

        target.afterEvaluate {
            def ext = target.extensions.findByName('nrouter') as NRouterExtension
            if (ext.name == null || ext.name.isEmpty()) {
                // 未设置name，则使用Project的name
                ext.name = target.name
            }
            if (ext.scheme.support && android != null) {
                if (ext.scheme.host == null) {
                    // 未设置host，则使用applicationId
                    ext.scheme.host = android.defaultConfig.getApplicationId()
                }
                android.applicationVariants.all { ApplicationVariant variant ->
                    variant.outputs.each { output ->
                        def task = output.processManifestProvider.get()
                        def manifestFile = new File(task.manifestOutputDirectory.get().asFile,
                                "AndroidManifest.xml")
                        task.doLast {
                            ManifestHandler.create(manifestFile, ext.scheme).handle()
                        }
                    }
                }
            }
        }
    }
}
