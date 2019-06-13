package club.fdawei.mourouter.api

import android.content.Context
import club.fdawei.mourouter.api.action.InjectAction
import club.fdawei.mourouter.api.action.InjectActionImpl
import club.fdawei.mourouter.api.action.RouteAction
import club.fdawei.mourouter.api.action.RouteActionImpl
import club.fdawei.mourouter.api.base.DataContainer
import club.fdawei.mourouter.api.inject.InjectManager
import club.fdawei.mourouter.api.log.DefaultLogger
import club.fdawei.mourouter.api.log.ILogger
import club.fdawei.mourouter.api.provider.ProviderLoader
import club.fdawei.mourouter.api.route.Router

/**
 * Created by david on 2019/05/24.
 */
object MouRouter {

    private var envs: DataContainer = DataContainer()
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
        this.router.loadRouteTable(providerLoader.provider)
        this.injectManager.loadInjectInfo(providerLoader.provider)
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