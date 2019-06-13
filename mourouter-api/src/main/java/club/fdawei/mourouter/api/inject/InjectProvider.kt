package club.fdawei.mourouter.api.inject

/**
 * Created by david on 2019/06/05.
 */
interface InjectProvider {
    fun provide(table: InjectTable)
}