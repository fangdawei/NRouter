package club.fdawei.nrouter.api.provider

import club.fdawei.nrouter.api.inject.InjectTable
import club.fdawei.nrouter.api.route.RouteTable

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