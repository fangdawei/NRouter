package club.fdawei.nrouter.api

import android.content.Context
import club.fdawei.nrouter.api.action.InjectAction
import club.fdawei.nrouter.api.action.InjectActionImpl
import club.fdawei.nrouter.api.action.RouteAction
import club.fdawei.nrouter.api.action.RouteActionImpl
import club.fdawei.nrouter.api.base.TypeDataContainer
import club.fdawei.nrouter.api.inject.InjectManager
import club.fdawei.nrouter.api.log.DefaultLogger
import club.fdawei.nrouter.api.log.ILogger
import club.fdawei.nrouter.api.provider.ProviderLoader
import club.fdawei.nrouter.api.route.Router

/**
 * Created by david on 2019/05/24.
 */
object NRouter {

    private var envs: TypeDataContainer = TypeDataContainer()
    private var router: Router
    private val injectManager: InjectManager
    private val providerLoader: ProviderLoader

    @JvmField
    var debug: Boolean = false

    @JvmField
    var logger: ILogger = DefaultLogger()

    init {
        router = Router(envs)
        injectManager = InjectManager()
        providerLoader = ProviderLoader()
    }

    @JvmStatic
    fun init(context: Context) {
        addEnv(context)
        router.loadRouteTable(providerLoader.provider)
        injectManager.loadInjectInfo(providerLoader.provider)
    }

    @JvmStatic
    fun addEnv(env: Any) {
        envs.put(env)
    }

    @JvmStatic
    fun route(uri: String): RouteAction {
        return RouteActionImpl(uri) {
            router.route(it)
        }
    }

    @JvmStatic
    fun injector(): InjectAction {
        return InjectActionImpl({
            injectManager.getInjector(it)
        }, {
            injectManager.getProvider(it)
        })
    }
}