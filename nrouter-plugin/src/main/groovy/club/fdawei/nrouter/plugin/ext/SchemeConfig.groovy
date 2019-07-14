package club.fdawei.nrouter.plugin.ext

/**
 * Created by david on 2019/07/03.
 */
class SchemeConfig {
    private boolean support = false
    private String scheme = 'nrouter'
    private String host

    boolean getSupport() {
        return support
    }

    String getScheme() {
        return scheme
    }

    String getHost() {
        return host
    }

    void setHostIfNull(CharSequence host) {
        if (this.host == null) {
            this.host = host
        }
    }

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