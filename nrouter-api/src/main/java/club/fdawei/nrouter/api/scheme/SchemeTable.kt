package club.fdawei.nrouter.api.scheme

import java.util.*

/**
 * Create by david on 2019/07/06.
 */
class SchemeTable {

    private val schemeMetaSet = TreeSet<SchemeMeta>()
    val schemeHandlers by lazy { schemeMetaSet.map { it.handler } }

    fun registerScheme(schemeMeta: SchemeMeta) {
        schemeMetaSet.add(schemeMeta)
    }
}