package club.fdawei.nrouter.api.route

/**
 * Create by david on 2019/05/30.
 */
class InterceptorMetaData(
    val interceptPath: String,
    val priority: Int,
    val interceptor: RouteInterceptor
) {
    override fun toString(): String {
        return "{" +
                "interceptPath='$interceptPath', " +
                "priority=$priority, " +
                "interceptor=${interceptor::class.qualifiedName}" +
                "}"
    }
}