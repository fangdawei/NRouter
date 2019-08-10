package club.fdawei.nrouter.api.scheme

import android.content.Intent
import club.fdawei.nrouter.api.NRouter
import club.fdawei.nrouter.api.common.COMMON_TAG
import club.fdawei.nrouter.api.provider.MultiProvider
import club.fdawei.nrouter.api.registry.SchemeRegistry

/**
 * Create by david on 2019/07/06.
 */
class SchemeManager {

    private val schemeTable = SchemeTable()

    fun loadSchemeTable(provider: MultiProvider?) {
        provider?.provide(schemeTable)
        NRouter.logger.d(COMMON_TAG, "load scheme table finish")
    }

    fun handleScheme(intent: Intent) {
        val iterator = schemeTable.getSchemeHandlers().iterator()
        while (iterator.hasNext()) {
            if (iterator.next().handle(intent)) {
                break
            }
        }
    }

    fun registry(): SchemeRegistry {
        return schemeTable
    }
}