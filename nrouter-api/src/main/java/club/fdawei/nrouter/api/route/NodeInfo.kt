package club.fdawei.nrouter.api.route

import kotlin.reflect.KClass

/**
 * Create by david on 2019/06/02.
 */
class NodeInfo(
    val target: KClass<out Any>,
    val flags: Int
)