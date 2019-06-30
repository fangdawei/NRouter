package club.fdawei.nrouter.api.log

import android.util.Log
import club.fdawei.nrouter.api.NRouter

/**
 * Create by david on 2019/05/26.
 */
class DefaultLogger : ILogger {
    override fun i(tag: String, msg: String) {
        Log.i(tag, msg)
    }

    override fun d(tag: String, msg: String) {
        if (NRouter.debug) {
            Log.d(tag, msg)
        }
    }

    override fun e(tag: String, msg: String) {
        Log.e(tag, msg)
    }

}