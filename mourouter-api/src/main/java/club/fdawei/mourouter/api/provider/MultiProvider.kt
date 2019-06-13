package club.fdawei.mourouter.api.provider

import club.fdawei.mourouter.api.inject.InjectTable
import club.fdawei.mourouter.api.route.RouteTable

/**
 * Create by david on 2019/05/25.
 */
interface MultiProvider {

    fun provide(routeTable: RouteTable) {

    }

    fun provide(injectTable: InjectTable) {

    }
}