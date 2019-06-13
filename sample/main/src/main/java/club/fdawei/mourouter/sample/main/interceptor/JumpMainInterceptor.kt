package club.fdawei.mourouter.sample.main.interceptor

import android.content.Context
import android.content.Intent
import android.widget.Toast
import club.fdawei.mourouter.annotation.Interceptor
import club.fdawei.mourouter.api.route.InterceptInvocation
import club.fdawei.mourouter.api.route.RouteInterceptor

/**
 * Create by david on 2019/06/02.
 */
@Interceptor(path = "/main/page/home")
class JumpMainInterceptor : RouteInterceptor {
    override fun intercept(invocation: InterceptInvocation) {
        val context = invocation.data.envs.get(Context::class, assignable = true)
        if (context != null) {
            Toast.makeText(context, "jump to main page", Toast.LENGTH_SHORT).show()
        }
        invocation.data.flags = invocation.data.flags.or(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        invocation.next()
    }
}