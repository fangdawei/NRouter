package club.fdawei.nrouter.sample.main

import android.util.Log
import club.fdawei.nrouter.sample.base.IPageLogger
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by david on 2019/07/02.
 */
class PageLogger : IPageLogger {

    private val pageLogs = mutableListOf<String>()

    override fun logPage(pageName: String) {
        val time = SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS", Locale.US).format(Date())
        pageLogs.add("$time enter $pageName")
    }

    override fun printLog() {
        pageLogs.forEach {
            Log.i("PageLog", it)
        }
    }
}