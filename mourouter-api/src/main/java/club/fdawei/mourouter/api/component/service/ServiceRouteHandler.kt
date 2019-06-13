package club.fdawei.mourouter.api.component.service

import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import club.fdawei.mourouter.api.action.RouteActionData
import club.fdawei.mourouter.api.route.HandleInfo
import club.fdawei.mourouter.api.route.RouteHandler
import club.fdawei.mourouter.api.util.ExceptionUtils

/**
 * Created by david on 2019/05/30.
 */
class ServiceRouteHandler(info: HandleInfo) : RouteHandler(info) {
    override fun go(data: RouteActionData) {
        if (info == null) {
            ExceptionUtils.exception("info is required ,but Null!")
            return
        }
        val context = data.envs.get(Context::class, assignable = true)
        if (context == null) {
            ExceptionUtils.exception("context is required, but Not Found!")
            return
        }
        val intent = Intent(context, info.target.java)
        when (data.envs.get(ActionType::class)) {
            ActionType.BIND -> {
                val conn = data.envs.get(ServiceConnection::class, assignable = true)
                if (conn != null) {
                    context.bindService(intent, conn, data.flags)
                }
            }
            ActionType.UNBIND -> {
                val conn = data.envs.get(ServiceConnection::class, assignable = true)
                if (conn != null) {
                    context.unbindService(conn)
                }
            }
            ActionType.STOP -> {
                context.stopService(intent)
            }
            else -> {
                context.startService(intent)
            }
        }
    }
}