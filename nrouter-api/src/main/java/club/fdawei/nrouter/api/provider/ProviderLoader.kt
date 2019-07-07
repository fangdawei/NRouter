package club.fdawei.nrouter.api.provider

import club.fdawei.nrouter.api.NRouter
import club.fdawei.nrouter.api.common.COMMON_TAG
import club.fdawei.nrouter.api.util.ExceptionUtils

/**
 * Create by david on 2019/06/06.
 */
const val APP_PROVIDER = "club.fdawei.nrouter.providers.App_Provider"

class ProviderLoader {
    val provider: MultiProvider? by lazy {
        try {
            Class.forName(APP_PROVIDER).newInstance() as? MultiProvider
        } catch (e: ClassNotFoundException) {
            NRouter.logger.e(COMMON_TAG, "App_Provider initialization error, class not found")
            null
        } catch (throwable: Throwable) {
            ExceptionUtils.exception("App_Provider initialization error, ${throwable.message}")
            null
        }
    }
}