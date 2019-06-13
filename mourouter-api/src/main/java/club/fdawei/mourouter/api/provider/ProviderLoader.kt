package club.fdawei.mourouter.api.provider

import club.fdawei.mourouter.api.util.ExceptionUtils

/**
 * Create by david on 2019/06/06.
 */
const val APP_PROVIDER = "club.fdawei.mourouter.providers.App_Provider"

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