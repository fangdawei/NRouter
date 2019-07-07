package club.fdawei.nrouter.api.scheme

/**
 * Create by david on 2019/07/06.
 */
class SchemeMeta(
    private val priority: Int,
    private val handlerCreator: () -> SchemeHandler
) : Comparable<SchemeMeta> {
    val handler by lazy { handlerCreator.invoke() }

    override fun compareTo(other: SchemeMeta): Int {
        return priority.compareTo(other.priority)
    }
}