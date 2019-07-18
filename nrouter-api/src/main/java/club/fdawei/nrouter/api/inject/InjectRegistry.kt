package club.fdawei.nrouter.api.inject

/**
 * Created by david on 2019/07/10.
 */
interface InjectRegistry {
    fun registerInjector(meta: InjectorMeta)
    fun registerProvider(meta: ProviderMeta)
}