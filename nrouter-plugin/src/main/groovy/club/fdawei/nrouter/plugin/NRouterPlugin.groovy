package club.fdawei.nrouter.plugin

import club.fdawei.nrouter.plugin.common.KaptArgKeys
import club.fdawei.nrouter.plugin.ext.NRouterExtension
import club.fdawei.nrouter.plugin.handler.ManifestHandler
import club.fdawei.nrouter.plugin.handler.NRouterTransform
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
        // create NRouterExtension
        def nrouterExt = target.extensions.create('nrouter', NRouterExtension)
        nrouterExt.name(target.name)

        // config kapt
        def kaptExt = target.extensions.findByName('kapt')
        if (kaptExt != null) {
            kaptExt.arguments {
                arg(KaptArgKeys.ARG_IS_APP, target.plugins.hasPlugin(AppPlugin))
            }
            nrouterExt.observeName({ name ->
                if (name != null) {
                    kaptExt.arguments {
                        arg(KaptArgKeys.ARG_MODULE_NAME, name)
                    }
                }
            })
        }

        def appExt = target.extensions.findByType(AppExtension)
        if (appExt != null) {
            appExt.registerTransform(new NRouterTransform())
        }

        target.afterEvaluate {
            if (nrouterExt.scheme.support && appExt != null) {
                nrouterExt.scheme.setHostIfNull(appExt.defaultConfig.getApplicationId())
                appExt.applicationVariants.all { ApplicationVariant variant ->
                    variant.outputs.all { output ->
                        def task = output.processManifestProvider.get()
                        def manifestFile = new File(task.manifestOutputDirectory.get().asFile,
                                "AndroidManifest.xml")
                        task.doLast {
                            ManifestHandler.create(manifestFile, nrouterExt.scheme).handle()
                        }
                    }
                }
            }
        }
    }
}
