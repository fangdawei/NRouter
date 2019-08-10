package club.fdawei.nrouter.api.registry

import club.fdawei.nrouter.api.inject.InjectorMeta
import club.fdawei.nrouter.api.inject.ProviderMeta
import club.fdawei.nrouter.api.route.InterceptorMeta
import club.fdawei.nrouter.api.route.RouteNodeMeta
import club.fdawei.nrouter.api.scheme.SchemeMeta

/**
 * Created by david on 2019/08/07.
 */
class RegistryDispatcher : RouteRegistry, InjectRegistry, SchemeRegistry {

    var routeRegistry: RouteRegistry? = null
    var injectRegistry: InjectRegistry? = null
    var schemeRegistry: SchemeRegistry? = null

    override fun register(meta: RouteNodeMeta) {
        routeRegistry?.register(meta)
    }

    override fun register(meta: InterceptorMeta) {
        routeRegistry?.register(meta)
    }

    override fun register(meta: InjectorMeta) {
        injectRegistry?.register(meta)
    }

    override fun register(meta: ProviderMeta) {
        injectRegistry?.register(meta)
    }

    override fun register(meta: SchemeMeta) {
        schemeRegistry?.register(meta)
    }
}