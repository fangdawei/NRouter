package club.fdawei.nrouter.api.component.creatable

import club.fdawei.nrouter.api.action.RouteActionBundle
import club.fdawei.nrouter.api.route.RouteHandler
import club.fdawei.nrouter.api.route.RouteNodeInfo
import club.fdawei.nrouter.api.util.ExceptionUtils
import club.fdawei.nrouter.api.util.withFlag

/**
 * Created by david on 2019/05/30.
 */
class CreatableRouteHandler : RouteHandler {
    override fun get(data: RouteActionBundle, info: RouteNodeInfo?): Any? {
        if (info == null) {
            ExceptionUtils.exception("info is required ,but Null!")
            return null
        }
        val creatable = when {
            info.flags.withFlag(FLAG_SINGLETON) -> {
                SingletonRepository.getOrCreate(info.target)
            }
            else -> {
                CreatableFactory.create(info.target)
            }
        } as? Creatable
        creatable?.init(Context.of(data.flags, data.extras))
        return creatable
    }
}