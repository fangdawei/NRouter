package club.fdawei.nrouter.api.route

import club.fdawei.nrouter.api.action.RouteActionData

/**
 * Create by david on 2019/05/30.
 */
abstract class RouteHandler(
    val info: NodeInfo?
) {
    open fun go(data: RouteActionData) {

    }

    open fun get(data: RouteActionData): Any? {
        return null
    }
}