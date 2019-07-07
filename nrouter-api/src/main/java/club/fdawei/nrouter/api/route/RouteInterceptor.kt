package club.fdawei.nrouter.api.route


/**
 * Create by david on 2019/05/26.
 */
interface RouteInterceptor {
    /**
     * true:后续拦截器将不被执行
     * false:执行下一个拦截器
     */
    fun intercept(context: InterceptContext): Boolean {
        return false
    }
}