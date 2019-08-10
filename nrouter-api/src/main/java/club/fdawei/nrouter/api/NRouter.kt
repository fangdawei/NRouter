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
import club.fdawei.nrouter.api.registry.RegistryDispatcher
import club.fdawei.nrouter.api.route.RouteManager
import club.fdawei.nrouter.api.scheme.SchemeManager
import club.fdawei.nrouter.api.util.parseRouteArgs
import club.fdawei.nrouter.api.util.safeThrowException

/**
 * Created by david on 2019/05/24.
 */
object NRouter {

    private val envs = TypeDataContainer()

    private val providerLoader = ProviderLoader()

    internal val routeManager = RouteManager()
    internal val injectManager = InjectManager()
    internal val schemeManager = SchemeManager()

    private val registryDispatcher by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
        RegistryDispatcher().apply {
            routeRegistry = routeManager.registry()
            injectRegistry = injectManager.registry()
            schemeRegistry = schemeManager.registry()
        }
    }

    @JvmField
    var debug: Boolean = false

    @JvmField
    var logger: ILogger = DefaultLogger()

    private var hasInitialized = false

    @JvmStatic
    fun init(context: Context) {
        if (hasInitialized) {
            safeThrowException("init has been called! Do not call again!")
            return
        }
        addEnv(context)
        routeManager.loadRouteTable(providerLoader.provider)
        injectManager.loadInjectInfo(providerLoader.provider)
        schemeManager.loadSchemeTable(providerLoader.provider)
        hasInitialized = true
    }

    @JvmStatic
    fun addEnv(env: Any) {
        envs.put(env)
    }

    private fun checkHasInitialized() {
        if (!hasInitialized) {
            safeThrowException("Not yet initialized, please call init first!")
        }
    }

    @JvmStatic
    fun registry(): RegistryDispatcher {
        checkHasInitialized()
        return registryDispatcher
    }

    @JvmStatic
    fun route(uri: String): RouteAction {
        checkHasInitialized()
        return RouteActionImpl(uri) {
            val args = it.uri.parseRouteArgs()
            args.forEach { (k, v) ->
                it.extras.putString(k, v)
            }
            it.args.putAll(envs)
            routeManager.route(it)
        }
    }

    @JvmStatic
    fun injector(): InjectAction {
        checkHasInitialized()
        return InjectActionImpl({
            injectManager.getInjector(it)
        }, {
            injectManager.getProvider(it)
        })
    }
}