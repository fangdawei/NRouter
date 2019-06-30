package club.fdawei.nrouter.api.component.instance

import club.fdawei.nrouter.api.action.RouteActionData
import club.fdawei.nrouter.api.route.NodeInfo
import club.fdawei.nrouter.api.route.RouteHandler
import club.fdawei.nrouter.api.util.ExceptionUtils
import club.fdawei.nrouter.api.util.withFlag

/**
 * Created by david on 2019/05/30.
 */
class InstanceRouteHandler(info: NodeInfo) : RouteHandler(info) {
    override fun get(data: RouteActionData): Any? {
        if (info == null) {
            ExceptionUtils.exception("info is required ,but Null!")
            return null
        }
        val instantiable = when {
            info.flags.withFlag(FLAG_SINGLETON) -> {
                SingletonRepository.getOrCreate(info.target)
            }
            else -> {
                InstanceFactory.create(info.target)
            }
        } as? Creatable
        instantiable?.init(Context.of(data.flags, data.extras))
        return instantiable
    }
}