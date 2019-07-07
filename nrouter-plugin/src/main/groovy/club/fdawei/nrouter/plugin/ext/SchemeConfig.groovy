package club.fdawei.nrouter.plugin.ext

/**
 * Created by david on 2019/07/03.
 */
class SchemeConfig {
    boolean support = false
    String scheme = 'nrouter'
    String host

    void support(boolean isSupport) {
        this.support = isSupport
    }

    void scheme(CharSequence scheme) {
        this.scheme = scheme
    }

    void host(CharSequence host) {
        this.host = host
    }
}