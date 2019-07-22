package club.fdawei.nrouter.api.provider

import club.fdawei.nrouter.api.NRouter
import club.fdawei.nrouter.api.common.COMMON_TAG
import club.fdawei.nrouter.api.util.safeThrowException

/**
 * Create by david on 2019/06/06.
 */
const val APP_PROVIDER = "club.fdawei.nrouter.generated.providers.NRouter_AppProvider"

class ProviderLoader {
    val provider: MultiProvider? by lazy {
        try {
            Class.forName(APP_PROVIDER).newInstance() as? MultiProvider
        } catch (e: ClassNotFoundException) {
            NRouter.logger.e(COMMON_TAG, "AppProvider initialization error, class not found")
            null
        } catch (throwable: Throwable) {
            safeThrowException("AppProvider initialization error, ${throwable.message}")
            null
        }
    }
}