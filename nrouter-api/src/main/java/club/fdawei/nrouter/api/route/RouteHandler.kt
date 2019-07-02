package club.fdawei.nrouter.api.route

import club.fdawei.nrouter.api.action.RouteActionBundle

/**
 * Create by david on 2019/05/30.
 */
abstract class RouteHandler(
    val info: RouteNodeInfo?
) {
    open fun go(data: RouteActionBundle) {

    }

    open fun get(data: RouteActionBundle): Any? {
        return null
    }
}