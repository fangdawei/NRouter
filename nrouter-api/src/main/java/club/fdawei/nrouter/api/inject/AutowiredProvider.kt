package club.fdawei.nrouter.api.inject

import club.fdawei.nrouter.api.NRouter
import club.fdawei.nrouter.api.action.ActionBundle
import kotlin.reflect.KClass

/**
 * Create by david on 2019/06/04.
 */
open class AutowiredProvider {
    open fun <T : Any> getAutowired(
        source: Any,
        name: String,
        path: String,
        type: KClass<T>,
        data: ActionBundle
    ): T? {
        return if (path.isNotBlank()) {
            NRouter.route(path)
                .arg(data.args)
                .withData(data.extras)
                .withFlags(data.flags)
                .get(type)
        } else {
            null
        }
    }
}