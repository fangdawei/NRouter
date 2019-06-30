package club.fdawei.nrouter.sample.main

import android.app.Application
import club.fdawei.nrouter.api.NRouter

/**
 * Created by david on 2019/05/29.
 */
class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        NRouter.debug = BuildConfig.DEBUG
        NRouter.init(this)
    }
}