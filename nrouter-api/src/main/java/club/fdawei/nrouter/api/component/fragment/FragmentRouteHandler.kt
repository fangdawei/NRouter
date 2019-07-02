package club.fdawei.nrouter.api.component.fragment

import android.support.v4.app.Fragment
import club.fdawei.nrouter.api.route.RouteNodeInfo
import club.fdawei.nrouter.api.route.RouteHandler
import club.fdawei.nrouter.api.util.ExceptionUtils

/**
 * Created by david on 2019/05/30.
 */
class FragmentRouteHandler(info: RouteNodeInfo) : RouteHandler(info) {
    override fun get(data: club.fdawei.nrouter.api.action.RouteActionBundle): Any? {
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