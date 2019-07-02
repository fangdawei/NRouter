package club.fdawei.nrouter.api.action

import kotlin.reflect.KClass

/**
 * Created by david on 2019/07/02.
 */
interface InstanceAction {
    fun register(clz: KClass<out Any>, instance: Any)
    fun <T : Any> get(clz: KClass<T>): T?
}