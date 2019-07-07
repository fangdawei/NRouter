package club.fdawei.nrouter.sample.main

import android.content.Intent
import club.fdawei.nrouter.annotation.Scheme
import club.fdawei.nrouter.api.NRouter
import club.fdawei.nrouter.api.scheme.SchemeHandler

/**
 * Create by david on 2019/07/06.
 */
@Scheme
class MainSchemeHandler : SchemeHandler {
    override fun handle(intent: Intent): Boolean {
        val uri = intent.data
        val queryMap = mutableMapOf<String, String>()
        if (uri != null && !uri.path.isNullOrBlank()) {
            uri.queryParameterNames.forEach { key ->
                uri.getQueryParameter(key)?.run {
                    queryMap[key] = this
                }
            }
            NRouter.route(uri.path!!)
                .withStrings(queryMap)
                .withFlags(intent.flags)
                .withData(intent.extras)
                .go()
        }
        return true
    }
}