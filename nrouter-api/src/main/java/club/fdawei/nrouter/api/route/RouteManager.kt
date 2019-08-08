package club.fdawei.nrouter.api.route

import club.fdawei.nrouter.api.NRouter
import club.fdawei.nrouter.api.action.RouteActionBundle
import club.fdawei.nrouter.api.common.COMMON_TAG
import club.fdawei.nrouter.api.provider.MultiProvider
import club.fdawei.nrouter.api.registry.RouteRegistry
import club.fdawei.nrouter.api.util.parseRoutePath

/**
 * Create by david on 2019/05/25.
 */
class RouteManager {
    private val routeTable = RouteTable()

    fun loadRouteTable(provider: MultiProvider?) {
        routeTable.clear()
        provider?.provide(routeTable)
        NRouter.logger.d(COMMON_TAG, "load route table finish ${routeTable.print()}")
    }

    fun route(bundle: RouteActionBundle): RouteResult? {
        val path = bundle.uri.parseRoutePath()
        val result = routeTable.addressing(path)
        return if (!check(bundle, result)) {
            null
        } else {
            RouteResult(
                result.routeNodeMeta?.nodeInfo,
                InterceptInvoker.of(result.interceptors).invoke(bundle, result.routeHandler)
            )
        }
    }

    private fun check(data: RouteActionBundle, result: AddressingResult): Boolean {
        return true
    }

    fun registry(): RouteRegistry {
        return routeTable
    }
}