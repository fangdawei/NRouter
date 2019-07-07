package club.fdawei.nrouter.api.route

import club.fdawei.nrouter.api.action.RouteActionBundle

/**
 * Created by david on 2019/07/03.
 */
class InterceptContext(
    val data: RouteActionBundle
) {
    var handler: RouteHandler? = null
}