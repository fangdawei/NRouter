package club.fdawei.nrouter.api.provider

import club.fdawei.nrouter.api.inject.InjectTable
import club.fdawei.nrouter.api.route.RouteTable

/**
 * Create by david on 2019/05/25.
 */
interface MultiProvider {

    fun provide(routeTable: RouteTable) {

    }

    fun provide(injectTable: InjectTable) {

    }
}