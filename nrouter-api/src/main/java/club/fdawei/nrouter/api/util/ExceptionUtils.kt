@file:JvmName("ExceptionUtils")

package club.fdawei.nrouter.api.util

import club.fdawei.nrouter.api.NRouter
import club.fdawei.nrouter.api.common.COMMON_TAG

/**
 * Create by david on 2019/05/26.
 */
fun safeThrowException(msg: String) {
    if (NRouter.debug) {
        throw RuntimeException(msg)
    } else {
        NRouter.logger.e(COMMON_TAG, msg)
    }
}