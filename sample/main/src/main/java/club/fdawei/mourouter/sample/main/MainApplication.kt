package club.fdawei.mourouter.sample.main

import android.app.Application
import club.fdawei.mourouter.api.MouRouter

/**
 * Created by david on 2019/05/29.
 */
class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        MouRouter.init(this)
        MouRouter.debug = BuildConfig.DEBUG
    }
}