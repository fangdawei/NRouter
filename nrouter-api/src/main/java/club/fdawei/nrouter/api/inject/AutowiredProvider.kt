package club.fdawei.nrouter.api.inject

import club.fdawei.nrouter.api.NRouter
import club.fdawei.nrouter.api.action.ActionData
import kotlin.reflect.KClass

/**
 * Create by david on 2019/06/04.
 */
open class AutowiredProvider {
    open fun <T : Any> getAutowired(
        source: Any,
        name: String,
        type: KClass<T>,
        data: ActionData
    ): T? {
        return NRouter.route(name)
            .env(data.envs)
            .withBundle(data.extras)
            .withFlags(data.flags)
            .get(type)
    }
}