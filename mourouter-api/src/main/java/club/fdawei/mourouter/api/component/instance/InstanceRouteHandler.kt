package club.fdawei.mourouter.api.component.instance

import club.fdawei.mourouter.api.action.RouteActionData
import club.fdawei.mourouter.api.route.HandleInfo
import club.fdawei.mourouter.api.route.RouteHandler
import club.fdawei.mourouter.api.util.ExceptionUtils
import club.fdawei.mourouter.api.util.withFlag

/**
 * Created by david on 2019/05/30.
 */
class InstanceRouteHandler(info: HandleInfo) : RouteHandler(info) {
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