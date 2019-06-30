package club.fdawei.nrouter.api.inject

import kotlin.reflect.KClass

/**
 * Create by david on 2019/06/07.
 */
class ProviderMetaData(
    val creator: () -> AutowiredProvider
) {
    val sources = mutableListOf<KClass<out Any>>()

    fun addSource(kClass: KClass<out Any>) {
        sources.add(kClass)
    }
}