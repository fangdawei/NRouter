package club.fdawei.nrouter.api.route


/**
 * Create by david on 2019/05/26.
 */
interface RouteInterceptor {
    fun intercept(invocation: InterceptInvocation)
}