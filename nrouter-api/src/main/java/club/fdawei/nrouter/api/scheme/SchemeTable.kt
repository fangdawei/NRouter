package club.fdawei.nrouter.api.scheme

import java.util.*

/**
 * Create by david on 2019/07/06.
 */
class SchemeTable : SchemeRegistry {

    private val schemeMetaSet = TreeSet<SchemeMeta>()
    val schemeHandlers by lazy { schemeMetaSet.map { it.handler } }

    override fun registerScheme(schemeMeta: SchemeMeta) {
        schemeMetaSet.add(schemeMeta)
    }
}