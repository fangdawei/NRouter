package club.fdawei.nrouter.api.provider

import club.fdawei.nrouter.api.inject.InjectRegistry
import club.fdawei.nrouter.api.route.RouteRegistry
import club.fdawei.nrouter.api.scheme.SchemeRegistry

/**
 * Create by david on 2019/05/25.
 */
interface MultiProvider {

    fun provide(registry: RouteRegistry) {

    }

    fun provide(registry: InjectRegistry) {

    }

    fun provide(registry: SchemeRegistry) {

    }
}