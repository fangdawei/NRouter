package club.fdawei.nrouter.api.base

import kotlin.reflect.KClass

/**
 * Create by david on 2019/07/07.
 */
class TypeBundle<T : Any>(
    val type: KClass<out T>,
    val creator: () -> T
)