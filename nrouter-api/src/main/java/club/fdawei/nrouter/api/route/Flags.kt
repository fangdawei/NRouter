@file:JvmName("RouteFlags")

package club.fdawei.nrouter.api.route

/**
 * Created by david on 2019/07/03.
 * 01~32 bit 通用标志
 * 33~64 bit 给RouterHandler自己定义
 */
const val FLAG_COMMON_BASE = 0x01L
const val FLAG_CUSTOM_BASE = FLAG_COMMON_BASE shl 32
