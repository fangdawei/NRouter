package club.fdawei.nrouter.api.component.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import club.fdawei.nrouter.api.route.NodeInfo
import club.fdawei.nrouter.api.route.RouteHandler
import club.fdawei.nrouter.api.util.ExceptionUtils

/**
 * Created by david on 2019/05/30.
 */
class ActivityRouteHandler(info: NodeInfo) : RouteHandler(info) {
    override fun go(data: club.fdawei.nrouter.api.action.RouteActionData) {
        if (info == null) {
            ExceptionUtils.exception("info is required ,but Null!")
            return
        }
        when (data.envs.get(ActivityOption::class)) {
            ActivityOption.START_FOR_RESULT -> {
                val activity = data.envs.get(Activity::class, assignable = true)
                if (activity == null) {
                    ExceptionUtils.exception(
                        "activity context is required when startActivityForResult, but Not Found!"
                    )
                    return
                }
                val requestCode = data.envs.get(RequestCode::class)
                val intent = Intent(activity, info.target.java)
                intent.flags = data.flags
                intent.putExtras(data.extras)
                activity.startActivityForResult(intent, requestCode?.value ?: 0)
            }
            else -> {
                var context: Context? = data.envs.get(Activity::class, assignable = true)
                if (context == null) {
                    context = data.envs.get(Context::class, assignable = true)
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