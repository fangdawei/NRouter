package club.fdawei.mourouter.api.route

import club.fdawei.mourouter.api.action.RouteActionData

/**
 * Created by david on 2019/05/31.
 */
class InterceptInvocation(
    private val interceptors: List<RouteInterceptor>,
    val data: RouteActionData
) {
    var routeHandler: RouteHandler? = null
    val iterator: Iterator<RouteInterceptor> by lazy { interceptors.iterator() }

    fun interrupt() {
        routeHandler = null
    }

    fun next() {
        if (iterator.hasNext()) {
            iterator.next().intercept(this)
        }
    }
}