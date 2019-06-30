package club.fdawei.nrouter.api.inject

import kotlin.reflect.KClass

/**
 * Created by david on 2019/06/05.
 */
class InjectTable {

    private val injectorMap = mutableMapOf<KClass<out Any>, InjectorKeeper>()
    private val providerMap = mutableMapOf<KClass<out Any>, ProviderKeeper>()

    fun registerInjector(metaData: InjectorMetaData) {
        injectorMap[metaData.target] = InjectorKeeper(metaData.creator)
    }

    fun registerProvider(metaData: ProviderMetaData) {
        val keeper = ProviderKeeper(metaData.creator)
        metaData.sources.forEach {
            providerMap[it] = keeper
        }
    }

    fun getInjector(kClass: KClass<out Any>): Injector? {
        return injectorMap[kClass]?.injector
    }

    fun getProvider(kClass: KClass<out Any>): AutowiredProvider? {
        return providerMap[kClass]?.provider
    }

    fun print(): String {
        val sirBuilder = StringBuilder()
        providerMap.forEach {
            sirBuilder.append("\n${it.key.qualifiedName} -> ${it.value.provider::class.qualifiedName}")
        }
        return sirBuilder.toString()
    }
}