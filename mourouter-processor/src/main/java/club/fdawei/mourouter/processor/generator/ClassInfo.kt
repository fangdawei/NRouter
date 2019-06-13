package club.fdawei.mourouter.processor.generator

/**
 * Created by david on 2019/05/28.
 */
object ClassInfo {

    const val PROVIDER_PACKAGE = "club.fdawei.mourouter.providers"
    const val PROVIDER_NAME_PREFIX = "Module_"
    const val PROVIDER_NAME_SUFFIX = "_Provider"
    const val APP_PROVIDER_NAME = "App_Provider"
    const val INJECTOR_NAME_SUFFIX = "_Injector"

    object Route {
        const val VAL_HANDLER = "handler"
    }

    object Provider {
        const val VAL_SOURCE = "sources"
    }

    object RouteTable {
        const val FUN_REGISTER_HANDLER = "registerHandler"
        const val FUN_REGISTER_INTERCEPTOR = "registerInterceptor"
    }

    object InjectTable {
        const val FUN_REGISTER_PROVIDER = "registerProvider"
        const val FUN_REGISTER_INJECTOR = "registerInjector"
    }

    object MultiProvider {
        const val FUN_PROVIDE_NAME = "provide"
        const val FUN_ARG_ROUTE_TABLE = "routeTable"
        const val FUN_ARG_INJECT_TABLE = "injectTable"
    }

    object AbsAppProvider {
        const val FUN_INIT_PROVIDERS = "initProviders"
    }

    object AutowiredProvider {
        const val FUN_GET_AUTOWIRED = "getAutowired"
    }

    object Injector {
        const val FUN_INJECT = "inject"
        const val FUN_ARG_SOURCE = "source"
        const val FUN_ARG_TARGET = "target"
        const val FUN_ARG_PROVIDER = "provider"
        const val FUN_ARG_DATA = "data"
    }

    object ProviderMetaData {
        const val FUN_ADD_SOURCE = "addSource"
    }
}