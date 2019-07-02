package club.fdawei.nrouter.api.action

import club.fdawei.nrouter.api.route.RouteHandler
import kotlin.reflect.KClass

/**
 * Created by david on 2019/06/04.
 */
class RouteActionImpl(
    override val uri: String,
    private val getHandler: ((RouteActionImpl) -> RouteHandler?)
) : RouteAction,
    RouteActionBundle, ActionWrapper<RouteAction>() {

    override val host: RouteAction = this
    private val routeHandler: RouteHandler? by lazy { getHandler.invoke(this) }

    override fun go() {
        routeHandler?.go(this)
    }

    override fun get(): Any? {
        return routeHandler?.get(this)
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : Any> get(clz: KClass<T>): T? {
        val instance = get()
        return if (clz.isInstance(instance)) instance as T else null
    }
}