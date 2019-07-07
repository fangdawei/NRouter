package club.fdawei.nrouter.api.route

import club.fdawei.nrouter.api.base.TypeBundle

/**
 * Create by david on 2019/05/30.
 */
class InterceptorMeta(
    val interceptPath: String,
    val priority: Int,
    val desc: String,
    private val typeBundle: TypeBundle<RouteInterceptor>
) {
    val interceptor by lazy { typeBundle.creator.invoke() }

    override fun toString(): String {
        return "$desc{" +
                "interceptPath='$interceptPath', " +
                "priority=$priority, " +
                "interceptor=${interceptor::class.qualifiedName}" +
                "}"
    }
}