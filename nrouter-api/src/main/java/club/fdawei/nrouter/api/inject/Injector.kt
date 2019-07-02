package club.fdawei.nrouter.api.inject

import club.fdawei.nrouter.api.action.ActionBundle

/**
 * Create by david on 2019/06/04.
 */
interface Injector {
    fun inject(
        target: Any,
        source: Any,
        provider: AutowiredProvider,
        data: ActionBundle
    )
}