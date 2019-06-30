package club.fdawei.nrouter.processor.common

/**
 * Created by david on 2019/06/06.
 */
class Context {
    var moduleName: String? = null
    var isAppModule: Boolean = false

    override fun toString(): String {
        return "Context(moduleName=$moduleName, isAppModule=$isAppModule)"
    }
}