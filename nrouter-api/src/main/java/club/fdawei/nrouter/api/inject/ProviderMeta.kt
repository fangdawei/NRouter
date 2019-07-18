package club.fdawei.nrouter.api.inject

import club.fdawei.nrouter.api.base.TypeBundle
import kotlin.reflect.KClass

/**
 * Create by david on 2019/06/07.
 */
class ProviderMeta(
    val typeBundle: TypeBundle<out AutowiredProvider>
) {
    val sources = mutableListOf<KClass<*>>()

    fun addSource(kClass: KClass<*>) {
        sources.add(kClass)
    }
}