package club.fdawei.nrouter.sample.main

import android.app.Application
import club.fdawei.nrouter.api.NRouter
import club.fdawei.nrouter.sample.main.pagelogger.PageLogger
import club.fdawei.nrouter.sample.main.pagelogger.PageLoggerProvider

/**
 * Created by david on 2019/05/29.
 */
class SampleApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        NRouter.debug = BuildConfig.DEBUG
        NRouter.init(this)

        NRouter.registry().register(
            PageLoggerProvider.routeNodeMeta(PageLogger("SamplePageLog"))
        )
    }
}