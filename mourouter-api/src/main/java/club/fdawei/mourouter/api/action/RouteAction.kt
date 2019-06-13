package club.fdawei.mourouter.api.action

import kotlin.reflect.KClass

/**
 * Created by david on 2019/06/04.
 */
interface RouteAction : Action<RouteAction> {
    fun go()

    fun get(): Any?

    fun <T : Any> get(clz: KClass<T>): T?
}