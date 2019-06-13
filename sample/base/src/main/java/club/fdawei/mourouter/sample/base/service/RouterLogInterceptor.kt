package club.fdawei.mourouter.sample.base.service

import android.util.Log
import club.fdawei.mourouter.annotation.Interceptor
import club.fdawei.mourouter.api.route.InterceptInvocation
import club.fdawei.mourouter.api.route.RouteInterceptor

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