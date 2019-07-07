package club.fdawei.nrouter.sample.base

import android.os.Binder
import android.util.Log

/**
 * Create by david on 2019/06/21.
 */
open class ServiceBinder : Binder() {
    open fun printName() {
        Log.i("ServiceBinder", "i am ServiceBinder")
    }
}