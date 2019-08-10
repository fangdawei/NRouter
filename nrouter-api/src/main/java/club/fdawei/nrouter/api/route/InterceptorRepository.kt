package club.fdawei.nrouter.api.route

import club.fdawei.nrouter.api.base.Keeper
import club.fdawei.nrouter.api.base.TypeBundle
import kotlin.reflect.KClass

/**
 * Created by david on 2019/08/08.
 */
object InterceptorRepository {

    private val interceptorMap = mutableMapOf<KClass<out RouteInterceptor>, Keeper<out RouteInterceptor>>()

    @Synchronized
    fun get(typeBundle: TypeBundle<out RouteInterceptor>): RouteInterceptor {
        var keeper = interceptorMap[typeBundle.type]
        if (keeper == null) {
            keeper = Keeper.of(typeBundle.creator).apply {
                interceptorMap[typeBundle.type] = this
            }
        }
        return keeper.instance
    }
}