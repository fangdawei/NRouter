package club.fdawei.mourouter.api.util

import club.fdawei.mourouter.api.MouRouter
import club.fdawei.mourouter.api.common.COMMON_TAG

/**
 * Create by david on 2019/05/26.
 */
object ExceptionUtils {
    fun exception(msg: String) {
        if (MouRouter.debug) {
            throw RuntimeException(msg)
        } else {
            MouRouter.logger.e(COMMON_TAG, msg)
        }
    }
}