package club.fdawei.nrouter.sample.main.interceptor

import android.content.Context
import android.content.Intent
import android.widget.Toast
import club.fdawei.nrouter.annotation.Interceptor
import club.fdawei.nrouter.api.route.InterceptContext
import club.fdawei.nrouter.api.route.RouteInterceptor

/**
 * Create by david on 2019/06/02.
 */
@Interceptor(path = "/main/page/home")
class JumpMainInterceptor : RouteInterceptor {
    override fun intercept(context: InterceptContext): Boolean {
        val ctx = context.data.args.get(Context::class, assignable = true)
        if (ctx != null) {
            Toast.makeText(ctx, "jump to main page", Toast.LENGTH_SHORT).show()
        }
        context.data.flags = context.data.flags.or(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        return false
    }
}