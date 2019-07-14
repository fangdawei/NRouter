package club.fdawei.nrouter.api.base

import kotlin.reflect.KClass

/**
 * Create by david on 2019/07/07.
 */
class TypeBundle<T : Any>(
    val type: KClass<T>,
    val creator: () -> T
) {
    companion object {
        @JvmStatic
        fun <T : Any> of(type: KClass<T>, creator: () -> T): TypeBundle<T> {
            return TypeBundle(type, creator)
        }
    }
}