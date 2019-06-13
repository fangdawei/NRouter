package club.fdawei.mourouter.api.inject

import club.fdawei.mourouter.api.MouRouter
import club.fdawei.mourouter.api.action.ActionData
import kotlin.reflect.KClass

/**
 * Create by david on 2019/06/04.
 */
open class AutowiredProvider {
    open fun <T : Any> getAutowired(source: Any, name: String, type: KClass<T>, data: ActionData): T? {
        return MouRouter.route(name)
            .env(data.envs)
            .withBundle(data.extras)
            .withFlags(data.flags)
            .get(type)
    }
}