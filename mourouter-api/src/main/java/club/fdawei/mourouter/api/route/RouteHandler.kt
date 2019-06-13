package club.fdawei.mourouter.api.route

import club.fdawei.mourouter.api.action.RouteActionData

/**
 * Create by david on 2019/05/30.
 */
abstract class RouteHandler(
    val info: HandleInfo?
) {
    open fun go(data: RouteActionData) {

    }

    open fun get(data: RouteActionData): Any? {
        return null
    }
}