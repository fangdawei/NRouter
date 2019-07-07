package club.fdawei.nrouter.plugin.common

/**
 * Created by david on 2019/05/29.
 */
final class ClassInfo {

    final static class AbsAppProvider {
        static final String NAME = 'club.fdawei.nrouter.api.provider.AbsAppProvider'
        static final String METHOD_INIT_PROVIDERS = 'initProviders'
        static final String METHOD_ADD_PROVIDER = 'addProvider'
    }

    final static class MultiProvider {
        static final String NAME = 'club.fdawei.nrouter.api.provider.MultiProvider'
    }

    final static class ModuleProvider {
        static boolean isModuleProvider(String fileName) {
            return fileName ==~ /^.*Module_.+_Provider\.class$/
        }
    }

    final static class AppProvider {
        static final String NAME = 'club.fdawei.nrouter.providers.App_Provider'
    }

    final static class SchemeDispatchActivity {
        static final String NAME = 'club.fdawei.nrouter.api.scheme.SchemeDispatchActivity'
    }
}
