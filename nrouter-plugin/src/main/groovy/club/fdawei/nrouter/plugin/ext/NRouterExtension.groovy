package club.fdawei.nrouter.plugin.ext

import org.gradle.api.Action

/**
 * Created by david on 2019/07/03.
 */
class NRouterExtension {

    private String name
    private SchemeConfig scheme = new SchemeConfig()

    private INameObserver nameObserver

    String getName() {
        return name
    }

    SchemeConfig getScheme() {
        return scheme
    }

    void observeName(INameObserver nameObserver) {
        this.nameObserver = nameObserver
        if (nameObserver != null) {
            nameObserver.onNameChanged(name)
        }
    }

    void name(CharSequence name) {
        this.name = name
        if (nameObserver != null) {
            nameObserver.onNameChanged(name)
        }
    }

    void scheme(Action<SchemeConfig> action) {
        action.execute(scheme)
    }
}
