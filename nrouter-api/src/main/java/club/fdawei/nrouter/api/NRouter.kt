package club.fdawei.nrouter.api

import android.content.Context
import android.content.Intent
import club.fdawei.nrouter.api.action.*
import club.fdawei.nrouter.api.base.TypeDataContainer
import club.fdawei.nrouter.api.inject.InjectManager
import club.fdawei.nrouter.api.instance.InstanceManager
import club.fdawei.nrouter.api.log.DefaultLogger
import club.fdawei.nrouter.api.log.ILogger
import club.fdawei.nrouter.api.provider.ProviderLoader
import club.fdawei.nrouter.api.route.RouteManager
import club.fdawei.nrouter.api.scheme.SchemeManager
import club.fdawei.nrouter.api.util.parseRouteArgs

/**
 * Created by david on 2019/05/24.
 */
object NRouter {

    private val envs = TypeDataContainer()

    private val providerLoader = ProviderLoader()
    private val routeManager = RouteManager()
    private val injectManager = InjectManager()
    private val instanceManager = InstanceManager()
    private val schemeManager = SchemeManager()

    @JvmField
    var debug: Boolean = false

    @JvmField
    var logger: ILogger = DefaultLogger()

    @JvmStatic
    fun init(context: Context) {
        addEnv(context)
        routeManager.loadRouteTable(providerLoader.provider)
        injectManager.loadInjectInfo(providerLoader.provider)
        schemeManager.loadSchemeTable(providerLoader.provider)
    }

    @JvmStatic
    fun addEnv(env: Any) {
        envs.put(env)
    }

    @JvmStatic
    fun route(uri: String): RouteAction {
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
        return InjectActionImpl({
            injectManager.getInjector(it)
        }, {
            injectManager.getProvider(it)
        })
    }

    @JvmStatic
    fun instance(): InstanceAction {
        return instanceManager
    }

    @JvmStatic
    internal fun scheme(intent: Intent) {
        schemeManager.handleScheme(intent)
    }
}