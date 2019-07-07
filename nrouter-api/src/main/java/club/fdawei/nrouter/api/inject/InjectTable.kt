package club.fdawei.nrouter.api.inject

import club.fdawei.nrouter.api.base.Keeper
import kotlin.reflect.KClass

/**
 * Created by david on 2019/06/05.
 */
class InjectTable {

    private val injectorMap = mutableMapOf<KClass<out Any>, Keeper<Injector>>()
    private val providerMap = mutableMapOf<KClass<out Any>, Keeper<AutowiredProvider>>()

    fun registerInjector(meta: InjectorMeta) {
        injectorMap[meta.target] = Keeper.of(meta.creator)
    }

    fun registerProvider(meta: ProviderMeta) {
        val keeper = Keeper.of(meta.creator)
        meta.sources.forEach {
            providerMap[it] = keeper
        }
    }

    fun getInjector(kClass: KClass<out Any>): Injector? {
        return injectorMap[kClass]?.instance
    }

    fun getProvider(kClass: KClass<out Any>): AutowiredProvider? {
        return providerMap[kClass]?.instance
    }

    fun print(): String {
        val sirBuilder = StringBuilder()
        providerMap.forEach { (clz, keeper) ->
            sirBuilder.append("\n${clz.qualifiedName} -> ${keeper.instance::class.qualifiedName}")
        }
        return sirBuilder.toString()
    }
}