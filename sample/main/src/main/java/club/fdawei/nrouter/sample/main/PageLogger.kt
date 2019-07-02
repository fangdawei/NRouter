package club.fdawei.nrouter.sample.main

import android.util.Log
import club.fdawei.nrouter.sample.base.IPageLogger

/**
 * Created by david on 2019/07/02.
 */
class PageLogger : IPageLogger {

    private val pageLogs = mutableListOf<String>()

    override fun logPage(pageName: String) {
        pageLogs.add(pageName)
    }

    override fun printLog() {
        pageLogs.forEach {
            Log.i("PageLog", it)
        }
    }
}