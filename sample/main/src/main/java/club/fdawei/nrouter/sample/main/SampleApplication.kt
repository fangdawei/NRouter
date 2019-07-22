package club.fdawei.nrouter.sample.main

import android.app.Application
import club.fdawei.nrouter.api.NRouter
import club.fdawei.nrouter.sample.base.IPageLogger

/**
 * Created by david on 2019/05/29.
 */
class SampleApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        NRouter.debug = BuildConfig.DEBUG
        NRouter.init(this)

        NRouter.container().register(IPageLogger::class, PageLogger())
    }
}