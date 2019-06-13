package club.fdawei.mourouter.api.inject

import club.fdawei.mourouter.api.action.ActionData

/**
 * Create by david on 2019/06/04.
 */
interface Injector {
    fun inject(target: Any, source: Any, provider: AutowiredProvider, data: ActionData)
}