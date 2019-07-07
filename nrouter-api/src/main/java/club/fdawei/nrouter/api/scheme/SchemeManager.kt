package club.fdawei.nrouter.api.scheme

import android.content.Intent
import club.fdawei.nrouter.api.provider.MultiProvider

/**
 * Create by david on 2019/07/06.
 */
class SchemeManager {

    private val schemeTable = SchemeTable()

    fun loadSchemeTable(provider: MultiProvider?) {
        provider?.provide(schemeTable)
    }

    fun handleScheme(intent: Intent) {
        val iterator = schemeTable.schemeHandlers.iterator()
        while (iterator.hasNext()) {
            if (iterator.next().handle(intent)) {
                break
            }
        }
    }
}