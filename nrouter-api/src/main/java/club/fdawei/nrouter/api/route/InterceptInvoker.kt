package club.fdawei.nrouter.api.route

import club.fdawei.nrouter.api.action.RouteActionData

/**
 * Create by david on 2019/06/01.
 */
class InterceptInvoker(
    private val interceptors: List<RouteInterceptor>
) {
    fun invoke(actionData: RouteActionData, origin: RouteHandler?): RouteHandler? {
        val invocation = InterceptInvocation(interceptors, actionData)
        invocation.routeHandler = origin
        invocation.next()
        return invocation.routeHandler
    }
}