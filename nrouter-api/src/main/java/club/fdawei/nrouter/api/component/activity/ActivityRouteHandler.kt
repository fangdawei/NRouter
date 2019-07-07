package club.fdawei.nrouter.api.component.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import club.fdawei.nrouter.api.action.RouteActionBundle
import club.fdawei.nrouter.api.component.activity.arg.ActivityOption
import club.fdawei.nrouter.api.component.activity.arg.RequestCode
import club.fdawei.nrouter.api.route.RouteHandler
import club.fdawei.nrouter.api.route.RouteNodeInfo
import club.fdawei.nrouter.api.util.ExceptionUtils

/**
 * Created by david on 2019/05/30.
 */
class ActivityRouteHandler : RouteHandler {
    override fun go(data: RouteActionBundle, info: RouteNodeInfo?) {
        if (info == null) {
            ExceptionUtils.exception("info is required ,but Null!")
            return
        }
        when (data.args.get(ActivityOption::class)) {
            ActivityOption.START_FOR_RESULT -> {
                val activity = data.args.get(Activity::class, assignable = true)
                if (activity == null) {
                    ExceptionUtils.exception(
                        "activity context is required when startActivityForResult, but Not Found!"
                    )
                    return
                }
                val requestCode = data.args.get(RequestCode::class)
                val intent = Intent(activity, info.target.java)
                intent.flags = data.flags
                intent.putExtras(data.extras)
                activity.startActivityForResult(intent, requestCode?.value ?: 0)
            }
            else -> {
                var context: Context? = data.args.get(Activity::class, assignable = true)
                if (context == null) {
                    context = data.args.get(Context::class, assignable = true)
                }
                if (context == null) {
                    ExceptionUtils.exception("context is required when startActivity, but Not Found!")
                    return
                }
                val intent = Intent(context, info.target.java)
                intent.flags = data.flags
                if (context !is Activity) {
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }
                intent.putExtras(data.extras)
                context.startActivity(intent)
            }
        }
    }
}