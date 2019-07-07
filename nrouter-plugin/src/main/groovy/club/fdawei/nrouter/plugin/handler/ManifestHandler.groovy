package club.fdawei.nrouter.plugin.handler

import club.fdawei.nrouter.plugin.common.ClassInfo
import club.fdawei.nrouter.plugin.ext.SchemeConfig
import club.fdawei.nrouter.plugin.log.PluginLogger
import groovy.xml.XmlUtil

/**
 * Create by david on 2019/07/03.
 */
class ManifestHandler {

    private File manifestFile
    private SchemeConfig schemeConfig
    private xmlParser = new XmlParser()

    private ManifestHandler(File manifestFile, SchemeConfig schemeConfig) {
        this.manifestFile = manifestFile
        this.schemeConfig = schemeConfig
    }

    void handle() {
        if (manifestFile == null || !manifestFile.exists()) {
            return
        }

        def manifest = xmlParser.parse(manifestFile)
        def activity = manifest.application[0].appendNode('activity',
                ['android:name' : ClassInfo.SchemeDispatchActivity.NAME,
                 'android:theme': '@android:style/Theme.NoDisplay'])
        def filter = activity.appendNode("intent-filter")
        filter.appendNode('action', ['android:name': 'android.intent.action.VIEW'])
        filter.appendNode('category', ['android:name': 'android.intent.category.DEFAULT'])
        filter.appendNode('category', ['android:name': 'android.intent.category.BROWSABLE'])
        filter.appendNode('data', ['android:scheme': "${schemeConfig.scheme}",
                                   'android:host'  : "${schemeConfig.host}"])

        def writer = new FileWriter(manifestFile)
        writer.write(XmlUtil.serialize(manifest))
        writer.flush()
        writer.close()
    }

    static ManifestHandler create(File manifestFile, SchemeConfig schemeConfig) {
        return new ManifestHandler(manifestFile, schemeConfig)
    }
}
