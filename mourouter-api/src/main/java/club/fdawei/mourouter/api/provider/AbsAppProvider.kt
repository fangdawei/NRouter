package club.fdawei.mourouter.api.provider

import club.fdawei.mourouter.api.inject.InjectTable
import club.fdawei.mourouter.api.route.RouteTable

/**
 * Created by david on 2019/06/06.
 */
abstract class AbsAppProvider : MultiProvider {

    private val providers = mutableListOf<MultiProvider>()

    init {
        this.initProviders()
    }

    abstract fun initProviders()

    fun addProvider(provider: MultiProvider) {
        providers.add(provider)
    }

    override fun provide(routeTable: RouteTable) {
        providers.forEach {
            it.provide(routeTable)
        }
    }

    override fun provide(injectTable: InjectTable) {
        providers.forEach {
            it.provide(injectTable)
        }
    }
}