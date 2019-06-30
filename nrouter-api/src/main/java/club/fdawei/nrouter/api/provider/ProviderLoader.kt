package club.fdawei.nrouter.api.provider

import club.fdawei.nrouter.api.util.ExceptionUtils

/**
 * Create by david on 2019/06/06.
 */
const val APP_PROVIDER = "club.fdawei.nrouter.providers.App_Provider"

class ProviderLoader {
    val provider: MultiProvider? by lazy {
        try {
            Class.forName(APP_PROVIDER).newInstance() as? MultiProvider
        } catch (e: Exception) {
            ExceptionUtils.exception("App_Provider initialization error, ${e.message}")
            null
        }
    }
}