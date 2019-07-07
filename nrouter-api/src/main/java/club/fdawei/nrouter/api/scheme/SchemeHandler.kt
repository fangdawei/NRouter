package club.fdawei.nrouter.api.scheme

import android.content.Intent

/**
 * Create by david on 2019/07/06.
 */
interface SchemeHandler {
    fun handle(intent: Intent): Boolean
}