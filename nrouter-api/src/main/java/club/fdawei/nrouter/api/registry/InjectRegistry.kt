package club.fdawei.nrouter.api.registry

import club.fdawei.nrouter.api.inject.InjectorMeta
import club.fdawei.nrouter.api.inject.ProviderMeta

/**
 * Created by david on 2019/07/10.
 */
interface InjectRegistry {
    fun register(meta: InjectorMeta)
    fun register(meta: ProviderMeta)
}