package club.fdawei.nrouter.api.route

import club.fdawei.nrouter.api.base.Keeper
import club.fdawei.nrouter.api.base.TypeBundle
import club.fdawei.nrouter.api.component.activity.ActivityRouteHandler
import club.fdawei.nrouter.api.component.creatable.CreatableRouteHandler
import club.fdawei.nrouter.api.component.fragment.FragmentRouteHandler
import club.fdawei.nrouter.api.component.service.ServiceRouteHandler
import java.util.concurrent.ConcurrentHashMap
import kotlin.reflect.KClass

/**
 * Create by david on 2019/07/06.
 */
object RouteHandlerRepository {

    private val routeHandlerMap = ConcurrentHashMap<KClass<out RouteHandler>, Keeper<out RouteHandler>>()

    init {
        register(ActivityRouteHandler::class) { ActivityRouteHandler() }
        register(ServiceRouteHandler::class) { ServiceRouteHandler() }
        register(FragmentRouteHandler::class) { FragmentRouteHandler() }
        register(CreatableRouteHandler::class) { CreatableRouteHandler() }
    }

    fun register(type: KClass<out RouteHandler>, creator: () -> RouteHandler) {
        synchronized(type) {
            if (!routeHandlerMap.contains(type)) {
                routeHandlerMap[type] = Keeper.of(creator)
            }
        }
    }

    fun register(typeBundle: TypeBundle<RouteHandler>) {
        register(typeBundle.type, typeBundle.creator)
    }

    fun get(type: KClass<out RouteHandler>): RouteHandler? {
        return routeHandlerMap[type]?.instance
    }
}