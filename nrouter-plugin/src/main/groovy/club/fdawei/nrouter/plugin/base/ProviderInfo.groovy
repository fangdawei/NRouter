package club.fdawei.nrouter.plugin.base

/**
 * Created by david on 2019/05/29.
 */
class ProviderInfo {
    From from
    File fromFile
    String name

    private ProviderInfo(From from, File fromFile, String name) {
        this.from = from
        this.fromFile = fromFile
        this.name = name
    }

    static ProviderInfo fromJar(File fromFile, String name) {
        return new ProviderInfo(From.JAR, fromFile, name)
    }

    static ProviderInfo fromDir(File fromFile, String name) {
        return new ProviderInfo(From.DIR, fromFile, name)
    }

    static enum From {
        DIR("dir"),
        JAR("jar")

        String value

        From(String value) {
            this.value = value
        }
    }
}
