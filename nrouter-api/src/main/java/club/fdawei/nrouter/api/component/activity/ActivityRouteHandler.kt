package club.fdawei.nrouter.api.component.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import club.fdawei.nrouter.api.action.RouteActionBundle
import club.fdawei.nrouter.api.component.activity.arg.ActivityOption
import club.fdawei.nrouter.api.component.activity.arg.OptionsBundle
import club.fdawei.nrouter.api.component.activity.arg.PendingTransition
import club.fdawei.nrouter.api.component.activity.arg.RequestCode
import club.fdawei.nrouter.api.route.RouteHandler
import club.fdawei.nrouter.api.route.RouteNodeInfo
import club.fdawei.nrouter.api.util.throwException

/**
 * Created by david on 2019/05/30.
 */
class ActivityRouteHandler : RouteHandler {
    override fun go(data: RouteActionBundle, info: RouteNodeInfo?) {
        if (info == null) {
            throwException("info is required ,but Null!")
            return
        }
        val options = data.args.get(OptionsBundle::class)?.value
        when (data.args.get(ActivityOption::class)) {
            ActivityOption.START_FOR_RESULT -> {
                val activity = data.args.get(Activity::class, assignable = true)
                if (activity == null) {
                    throwException(
                        "activity context is required when startActivityForResult, but Not Found!"
                    )
                    return
                }
                val requestCode = data.args.get(RequestCode::class)?.value ?: 0
                val intent = Intent(activity, info.target.java)
                intent.flags = data.flags
                intent.putExtras(data.extras)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    activity.startActivityForResult(intent, requestCode, options)
                } else {
                    activity.startActivityForResult(intent, requestCode)
                }
                val pt = data.args.get(PendingTransition::class)
                if (pt != null) {
                    activity.overridePendingTransition(pt.enterAnim, pt.exitAnim)
                }
            }
            else -> {
                var context: Context? = data.args.get(Activity::class, assignable = true)
                if (context == null) {
                    context = data.args.get(Context::class, assignable = true)
                }
                if (context == null) {
                    throwException("context is required when startActivity, but Not Found!")
                    return
                }
                val intent = Intent(context, info.target.java)
                intent.flags = data.flags
                if (context !is Activity) {
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }
                intent.putExtras(data.extras)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    context.startActivity(intent, options)
                } else {
                    context.startActivity(intent)
                }
                val pt = data.args.get(PendingTransition::class)
                if (context is Activity && pt != null) {
                    context.overridePendingTransition(pt.enterAnim, pt.exitAnim)
                }
            }
        }
    }
}