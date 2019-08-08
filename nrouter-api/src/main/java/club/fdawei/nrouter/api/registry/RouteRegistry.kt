package club.fdawei.nrouter.api.registry

import club.fdawei.nrouter.api.route.InterceptorMeta
import club.fdawei.nrouter.api.route.RouteNodeMeta

/**
 * Create by david on 2019/07/10.
 */
interface RouteRegistry {
    fun register(meta: RouteNodeMeta)
    fun register(meta: InterceptorMeta)
}