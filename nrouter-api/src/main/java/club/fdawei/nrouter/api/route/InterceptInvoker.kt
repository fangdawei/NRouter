package club.fdawei.nrouter.api.route

import club.fdawei.nrouter.api.action.RouteActionBundle

/**
 * Create by david on 2019/06/01.
 */
class InterceptInvoker private constructor(
    private val interceptors: List<RouteInterceptor>
) {
    fun invoke(actionData: RouteActionBundle, origin: RouteHandler?): RouteHandler? {
        val context = InterceptContext(actionData).apply {
            this.handler = origin
        }
        val iterator = interceptors.iterator()
        while (iterator.hasNext()) {
            if (!iterator.next().intercept(context)) {
                break
            }
        }
        return context.handler
    }

    companion object {
        fun of(interceptors: List<RouteInterceptor>): InterceptInvoker {
            return InterceptInvoker(interceptors)
        }
    }
}