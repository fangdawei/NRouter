package club.fdawei.nrouter.api.inject

import club.fdawei.nrouter.api.NRouter
import club.fdawei.nrouter.api.common.COMMON_TAG
import club.fdawei.nrouter.api.provider.MultiProvider
import club.fdawei.nrouter.api.registry.InjectRegistry

/**
 * Created by david on 2019/06/05.
 */
class InjectManager {

    private val injectTable = InjectTable()

    fun loadInjectInfo(provider: MultiProvider?) {
        provider?.provide(injectTable)
        NRouter.logger.d(COMMON_TAG, "load inject table finish ${injectTable.print()}")
    }

    fun getInjector(target: Any): Injector? {
        return injectTable.getInjector(target::class)
    }

    fun getProvider(source: Any): AutowiredProvider? {
        return injectTable.getProvider(source::class)
    }

    fun registry(): InjectRegistry {
        return injectTable
    }
}