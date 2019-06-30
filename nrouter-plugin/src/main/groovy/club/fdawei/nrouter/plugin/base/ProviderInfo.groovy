package club.fdawei.nrouter.plugin.base

/**
 * Created by david on 2019/05/29.
 */
class ProviderInfo {
    File dir
    String name

    ProviderInfo(File dir, String name) {
        this.dir = dir
        this.name = name
    }


    @Override
    String toString() {
        return "ProviderInfo{" +
                "dir=" + dir +
                ", name='" + name + '\'' +
                '}'
    }
}
