package club.fdawei.nrouter.api.action

import club.fdawei.nrouter.api.inject.AutowiredProvider

/**
 * Created by david on 2019/06/04.
 */
interface InjectAction : Action<InjectAction> {

    fun inject(target: Any)

    fun inject(target: Any, source: Any)

    fun inject(target: Any, source: Any, provider: AutowiredProvider)
}