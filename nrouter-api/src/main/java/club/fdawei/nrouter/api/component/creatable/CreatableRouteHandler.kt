package club.fdawei.nrouter.api.component.creatable

import club.fdawei.nrouter.api.NRouter
import club.fdawei.nrouter.api.action.RouteActionBundle
import club.fdawei.nrouter.api.common.COMMON_TAG
import club.fdawei.nrouter.api.route.RouteHandler
import club.fdawei.nrouter.api.route.RouteNodeInfo
import club.fdawei.nrouter.api.util.safeThrowException
import club.fdawei.nrouter.api.util.withFlag
import kotlin.reflect.KClass
import kotlin.reflect.full.isSubclassOf

/**
 * Created by david on 2019/05/30.
 */
@Suppress("UNCHECKED_CAST")
class CreatableRouteHandler : RouteHandler {
    override fun get(data: RouteActionBundle, info: RouteNodeInfo?): Any? {
        if (info == null) {
            safeThrowException("info is required ,but Null!")
            return null
        }
        if (!info.target.isSubclassOf(Creatable::class)) {
            NRouter.logger.e(COMMON_TAG, "${info.target.qualifiedName} is not Creatable")
            return null
        }
        val type = info.target as KClass<out Creatable>
        val creatable = when {
            info.flags.withFlag(FLAG_SINGLETON) -> {
                SingletonRepository.getOrCreate(type)
            }
            else -> {
                CreatableFactory.create(type)
            }
        } as? Creatable
        creatable?.init(ArgBundle.of(data.flags, data.extras))
        return creatable
    }
}