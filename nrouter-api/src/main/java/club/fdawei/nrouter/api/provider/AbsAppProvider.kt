package club.fdawei.nrouter.api.provider

import club.fdawei.nrouter.api.inject.InjectTable
import club.fdawei.nrouter.api.route.RouteTable
import club.fdawei.nrouter.api.scheme.SchemeTable

/**
 * Created by david on 2019/06/06.
 */
open class AbsAppProvider : MultiProvider {

    private val providers = mutableListOf<MultiProvider>()

    init {
        this.initProviders()
    }

    open fun initProviders() {

    }

    fun addProvider(provider: MultiProvider) {
        providers.add(provider)
    }

    override fun provide(routeTable: RouteTable) {
        for (provider in providers) {
            provider.provide(routeTable)
        }
    }

    override fun provide(injectTable: InjectTable) {
        for (provider in providers) {
            provider.provide(injectTable)
        }
    }

    override fun provide(schemeTable: SchemeTable) {
        for (provider in providers) {
            provider.provide(schemeTable)
        }
    }
}