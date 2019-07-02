package club.fdawei.nrouter.api.route

import club.fdawei.nrouter.api.NRouter
import club.fdawei.nrouter.api.action.RouteActionImpl
import club.fdawei.nrouter.api.base.TypeDataContainer
import club.fdawei.nrouter.api.common.COMMON_TAG
import club.fdawei.nrouter.api.provider.MultiProvider
import club.fdawei.nrouter.api.util.parseRouteArgs
import club.fdawei.nrouter.api.util.parseRoutePath

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
        NRouter.logger.d(COMMON_TAG, "load route table finish ${routeTable.print()}")
    }

    fun route(action: RouteActionImpl): RouteHandler? {
        val path = action.uri.parseRoutePath()
        val args = action.uri.parseRouteArgs()
        args.forEach { (k, v) -> action.withString(k, v) }
        action.env(baseEnvs)
        val routeResult = routeTable.addressing(path)
        val handler = routeResult.routeNodeMetaData?.handler
        val sortedInterceptors = routeResult.sortedInterceptors
        return InterceptInvoker(sortedInterceptors).invoke(action, handler)
    }
}