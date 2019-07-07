package club.fdawei.nrouter.sample.base

import android.util.Log
import club.fdawei.nrouter.annotation.Interceptor
import club.fdawei.nrouter.api.route.InterceptContext
import club.fdawei.nrouter.api.route.RouteInterceptor

/**
 * Create by david on 2019/05/31.
 */
@Interceptor(desc = "路由日志记录")
class RouterLogInterceptor : RouteInterceptor {
    override fun intercept(context: InterceptContext): Boolean {
        Log.i("RouterLog", "route uri=${context.data.uri}")
        return false
    }
}