package club.fdawei.nrouter.api.route

import club.fdawei.nrouter.api.base.Keeper
import club.fdawei.nrouter.api.base.TypeBundle
import kotlin.reflect.KClass

/**
 * Create by david on 2019/07/06.
 */
object RouteHandlerRepository {

    private val routeHandlerMap = mutableMapOf<KClass<out RouteHandler>, Keeper<out RouteHandler>>()

    @Synchronized
    fun get(typeBundle: TypeBundle<out RouteHandler>): RouteHandler? {
        var keeper = routeHandlerMap[typeBundle.type]
        if (keeper == null) {
            keeper = Keeper.of(typeBundle.creator).apply {
                routeHandlerMap[typeBundle.type] = this
            }
        }
        return keeper.instance
    }
}