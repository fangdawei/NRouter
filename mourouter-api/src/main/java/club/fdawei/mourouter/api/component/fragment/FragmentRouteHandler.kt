package club.fdawei.mourouter.api.component.fragment

import android.support.v4.app.Fragment
import club.fdawei.mourouter.api.action.RouteActionData
import club.fdawei.mourouter.api.route.HandleInfo
import club.fdawei.mourouter.api.route.RouteHandler
import club.fdawei.mourouter.api.util.ExceptionUtils

/**
 * Created by david on 2019/05/30.
 */
class FragmentRouteHandler(info: HandleInfo) : RouteHandler(info) {
    override fun get(data: RouteActionData): Any? {
        if (info == null) {
            ExceptionUtils.exception("info is required ,but Null!")
            return null
        }
        return try {
            val instance = info.target.java.newInstance()
            if (instance is Fragment) {
                instance.arguments = data.extras
            }
            instance
        } catch (e: Exception) {
            ExceptionUtils.exception("create fragment error, ${e.message}")
            null
        }
    }
}