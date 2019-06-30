package club.fdawei.nrouter.plugin.log


/**
 * Created by david on 2019/05/27.
 */
class PluginLogger {

    static void i(String tag, String msg) {
        System.out.println("${tag} ${msg}")
    }

    static void e(String tag, String msg) {
        System.err.println("${tag} ${msg}")
    }
}
