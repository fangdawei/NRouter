package club.fdawei.mourouter.api.route

import club.fdawei.mourouter.api.MouRouter
import club.fdawei.mourouter.api.action.RouteActionImpl
import club.fdawei.mourouter.api.base.TypeDataContainer
import club.fdawei.mourouter.api.common.COMMON_TAG
import club.fdawei.mourouter.api.provider.MultiProvider
import club.fdawei.mourouter.api.util.parseRouteArgs
import club.fdawei.mourouter.api.util.parseRoutePath

/**
 * Create by david on 2019/05/25.
 */
class Router(
    private val baseEnvs: TypeDataContainer
) {

    private val routeTable = RouteTable()

    fun loadRouteTable(provider: MultiProvider?) {
        routeTable.clear()
        provider?.provide(routeTable)
        MouRouter.logger.d(COMMON_TAG, "load route table finish ${routeTable.print()}")
    }

    fun route(action: RouteActionImpl): RouteHandler? {
        val path = action.uri.parseRoutePath()
        val args = action.uri.parseRouteArgs()
        args.forEach { (k, v) -> action.withString(k, v) }
        action.env(baseEnvs)
        val routeResult = routeTable.addressing(path)
        val handler = routeResult.handlerMetaData?.handler
        val sortedInterceptors = routeResult.sortedInterceptors
        return InterceptInvoker(sortedInterceptors).invoke(action, handler)
    }
}