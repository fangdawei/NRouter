package club.fdawei.nrouter.api.scheme

import club.fdawei.nrouter.api.base.TypeBundle

/**
 * Create by david on 2019/07/06.
 */
class SchemeMeta(
    private val priority: Int,
    private val handlerBundle: TypeBundle<out SchemeHandler>
) : Comparable<SchemeMeta> {
    val handler by lazy { handlerBundle.creator.invoke() }

    override fun compareTo(other: SchemeMeta): Int {
        return priority.compareTo(other.priority)
    }
}