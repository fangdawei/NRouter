package club.fdawei.nrouter.api.route

/**
 * Create by david on 2019/06/01.
 */
class AddressingResult(
    val routeNodeMeta: RouteNodeMeta?,
    val routeHandler: RouteHandler?,
    private val interceptors: List<InterceptorMeta>
) {
    val sortedInterceptors: List<RouteInterceptor> by lazy {
        interceptors.sortedBy { it.priority }.map { it.interceptor }
    }
}