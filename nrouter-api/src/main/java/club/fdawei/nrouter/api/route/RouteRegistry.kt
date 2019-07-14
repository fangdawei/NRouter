package club.fdawei.nrouter.api.route

/**
 * Create by david on 2019/07/10.
 */
interface RouteRegistry {
    fun registerRouteNode(routeNodeMeta: RouteNodeMeta)
    fun registerInterceptor(interceptor: InterceptorMeta)
}