package club.fdawei.nrouter.plugin.ext

import org.gradle.api.Action

/**
 * Created by david on 2019/07/03.
 */
class NRouterExtension {

    private String name
    private SchemeConfig scheme = new SchemeConfig()

    String getName() {
        return name
    }

    void setName(String name) {
        this.name = name
    }

    SchemeConfig getScheme() {
        return scheme
    }

    void name(CharSequence name) {
        this.name = name
    }

    void scheme(Action<SchemeConfig> action) {
        action.execute(scheme)
    }
}
