package club.fdawei.nrouter.api.action

import club.fdawei.nrouter.api.route.RouteResult
import kotlin.reflect.KClass

/**
 * Created by david on 2019/06/04.
 */
class RouteActionImpl(
    override val uri: String,
    private val router: ((RouteActionBundle) -> RouteResult?)
) : RouteAction, RouteActionBundle, ActionWrapper<RouteAction>() {

    override val host: RouteAction = this
    private val result: RouteResult? by lazy { router.invoke(this) }

    override fun go() {
        result?.handler?.go(this, result?.node)
    }

    override fun get(): Any? {
        return result?.handler?.get(this, result?.node)
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : Any> get(clz: KClass<T>): T? {
        val instance = get()
        return if (clz.isInstance(instance)) instance as T else null
    }
}