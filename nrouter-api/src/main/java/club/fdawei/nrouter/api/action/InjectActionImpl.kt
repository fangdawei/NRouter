package club.fdawei.nrouter.api.action

import club.fdawei.nrouter.api.inject.AutowiredProvider
import club.fdawei.nrouter.api.inject.Injector

/**
 * Created by david on 2019/06/04.
 */
class InjectActionImpl(
    private val getInjector: (Any) -> Injector?,
    private val getProvider: (Any) -> AutowiredProvider?
) : InjectAction, ActionWrapper<InjectAction>() {

    override val host: InjectAction = this

    override fun inject(target: Any) {
        inject(target, target)
    }

    override fun inject(target: Any, source: Any) {
        val injector = getInjector.invoke(target)
        val provider = getProvider.invoke(source)
        inject(target, injector, source, provider)
    }

    override fun inject(target: Any, source: Any, provider: AutowiredProvider) {
        val injector = getInjector.invoke(target)
        inject(target, injector, source, provider)
    }

    private fun inject(target: Any, injector: Injector?, source: Any, provider: AutowiredProvider?) {
        if (injector == null || provider == null) {
            return
        }
        injector.inject(target, source, provider, this)
    }
}