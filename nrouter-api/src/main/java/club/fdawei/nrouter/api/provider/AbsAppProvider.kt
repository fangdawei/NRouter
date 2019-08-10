package club.fdawei.nrouter.api.provider

import club.fdawei.nrouter.api.registry.InjectRegistry
import club.fdawei.nrouter.api.registry.RouteRegistry
import club.fdawei.nrouter.api.registry.SchemeRegistry

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

    override fun provide(registry: RouteRegistry) {
        for (provider in providers) {
            provider.provide(registry)
        }
    }

    override fun provide(registry: InjectRegistry) {
        for (provider in providers) {
            provider.provide(registry)
        }
    }

    override fun provide(registry: SchemeRegistry) {
        for (provider in providers) {
            provider.provide(registry)
        }
    }
}