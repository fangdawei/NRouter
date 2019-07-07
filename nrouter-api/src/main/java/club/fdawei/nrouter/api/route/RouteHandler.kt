package club.fdawei.nrouter.api.route

import club.fdawei.nrouter.api.action.RouteActionBundle

/**
 * Create by david on 2019/05/30.
 */
interface RouteHandler {
    fun go(data: RouteActionBundle, info: RouteNodeInfo?) {

    }

    fun get(data: RouteActionBundle, info: RouteNodeInfo?): Any? {
        return null
    }
}