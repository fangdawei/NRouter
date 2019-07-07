package club.fdawei.nrouter.api.route

/**
 * Create by david on 2019/05/30.
 */
class InterceptorMeta(
    val interceptPath: String,
    val priority: Int,
    val desc: String,
    private val creator: () -> RouteInterceptor
) {
    val interceptor by lazy { creator.invoke() }

    override fun toString(): String {
        return "$desc{" +
                "interceptPath='$interceptPath', " +
                "priority=$priority, " +
                "interceptor=${interceptor::class.qualifiedName}" +
                "}"
    }
}