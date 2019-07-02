package club.fdawei.nrouter.sample.base

import android.util.Log
import club.fdawei.nrouter.annotation.Interceptor
import club.fdawei.nrouter.api.route.InterceptInvocation
import club.fdawei.nrouter.api.route.RouteInterceptor

/**
 * Create by david on 2019/05/31.
 */
@Interceptor
class RouterLogInterceptor : RouteInterceptor {
    override fun intercept(invocation: InterceptInvocation) {
        Log.i("RouterLog", "route uri=${invocation.data.uri}")
        invocation.next()
    }
}