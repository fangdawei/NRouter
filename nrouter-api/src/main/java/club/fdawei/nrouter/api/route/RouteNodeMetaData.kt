package club.fdawei.nrouter.api.route

import kotlin.reflect.KClass

/**
 * Created by david on 2019/05/27.
 */
class RouteNodeMetaData(
    val path: String,
    val target: KClass<out Any>,
    val flags: Int,
    val desc: String,
    private val getHandler: (RouteNodeInfo) -> RouteHandler
) {
    val handler: RouteHandler by lazy {
        getHandler.invoke(RouteNodeInfo(target, flags))
    }

    override fun toString(): String {
        return "$desc{" +
                "path='$path', " +
                "target=${target.qualifiedName}, " +
                "flags=$flags, " +
                "handler=${handler::class.qualifiedName}" +
                "}"
    }
}