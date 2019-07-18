package club.fdawei.nrouter.api.component.fragment

import android.support.v4.app.Fragment
import club.fdawei.nrouter.api.action.RouteActionBundle
import club.fdawei.nrouter.api.route.RouteHandler
import club.fdawei.nrouter.api.route.RouteNodeInfo
import club.fdawei.nrouter.api.util.safeThrowException

/**
 * Created by david on 2019/05/30.
 */
class FragmentRouteHandler : RouteHandler {
    override fun get(data: RouteActionBundle, info: RouteNodeInfo?): Any? {
        if (info == null) {
            safeThrowException("info is required ,but Null!")
            return null
        }
        return try {
            val instance = info.target.java.newInstance()
            if (instance is Fragment) {
                instance.arguments = data.extras
            }
            instance
        } catch (e: Exception) {
            safeThrowException("create fragment error, ${e.message}")
            null
        }
    }
}