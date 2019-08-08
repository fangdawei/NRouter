package club.fdawei.nrouter.api.base

import kotlin.reflect.KClass

/**
 * Created by david on 2019/08/06.
 */
class TypeInstanceBundle<T : Any>(
    type: KClass<T>,
    instance: T
) : TypeBundle<T>(type, { instance }) {
    companion object {
        @JvmStatic
        fun <T : Any> of(type: KClass<T>, instance: T): TypeInstanceBundle<T> {
            return TypeInstanceBundle(type, instance)
        }
    }
}