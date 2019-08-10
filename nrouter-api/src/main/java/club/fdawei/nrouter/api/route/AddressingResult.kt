package club.fdawei.nrouter.api.route

/**
 * Create by david on 2019/06/01.
 */
class AddressingResult(
    val routeNodeMeta: RouteNodeMeta?,
    val routeHandler: RouteHandler?,
    val interceptors: List<RouteInterceptor>
)