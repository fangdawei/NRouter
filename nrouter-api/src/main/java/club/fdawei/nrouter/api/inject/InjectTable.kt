package club.fdawei.nrouter.api.inject

import android.app.Activity
import club.fdawei.nrouter.api.base.Keeper
import club.fdawei.nrouter.api.base.LRULinkedHashMap
import club.fdawei.nrouter.api.component.activity.ActivityAutowiredProvider
import club.fdawei.nrouter.api.component.fragment.FragmentAutowiredProvider
import kotlin.reflect.KClass
import kotlin.reflect.full.superclasses

/**
 * Created by david on 2019/06/05.
 */
class InjectTable : InjectRegistry {

    private val injectorMap = mutableMapOf<KClass<*>, Keeper<out Injector>>()
    private val providerMap = mutableMapOf<KClass<*>, Keeper<out AutowiredProvider>>()
    private val providerCache = LRULinkedHashMap<KClass<*>, Keeper<out AutowiredProvider>>(64)

    init {
        Keeper.of { AutowiredProvider() }.let {
            providerMap[Any::class] = it
        }
        Keeper.of { ActivityAutowiredProvider() }.let {
            providerMap[Activity::class] = it
        }
        Keeper.of { FragmentAutowiredProvider() }.let {
            @Suppress("DEPRECATION")
            providerMap[android.app.Fragment::class] = it
            providerMap[android.support.v4.app.Fragment::class] = it
        }
    }

    override fun registerInjector(meta: InjectorMeta) {
        injectorMap[meta.target] = Keeper.of(meta.injectorBundle.creator)
    }

    override fun registerProvider(meta: ProviderMeta) {
        val keeper = Keeper.of(meta.typeBundle.creator)
        meta.sources.forEach {
            registerProvider(it, keeper)
        }
    }

    private fun registerProvider(type: KClass<*>, keeper: Keeper<out AutowiredProvider>) {
        providerMap[type] = keeper
        if (providerCache.isNotEmpty()) {
            providerCache.clear()
        }
    }

    fun getInjector(kClass: KClass<*>): Injector? {
        return injectorMap[kClass]?.instance
    }

    fun getProvider(kClass: KClass<*>): AutowiredProvider? {
        providerCache[kClass]?.run {
            return this.instance
        }
        var types = mutableListOf<KClass<*>>(kClass)
        while (types.isNotEmpty()) {
            types.forEach {
                providerMap[it]?.run {
                    providerCache[kClass] = this
                    return this.instance
                }
            }
            val parents = mutableListOf<KClass<*>>()
            types.forEach { type ->
                type.superclasses.forEach { superType ->
                    if (!type.java.isInterface && !superType.java.isInterface) {
                        parents.add(0, superType)
                    } else if (!type.java.isInterface) {
                        parents.add(superType)
                    } else if (type.java.isInterface && superType != Any::class) {
                        parents.add(superType)
                    }
                }
            }
            types = parents
        }
        return null
    }

    fun print(): String {
        val builder = StringBuilder()
        providerMap.forEach { (clz, keeper) ->
            builder.append("\n${clz.qualifiedName} -> ${keeper.instance::class.qualifiedName}")
        }
        return builder.toString()
    }
}