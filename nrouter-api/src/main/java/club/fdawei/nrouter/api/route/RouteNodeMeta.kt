package club.fdawei.nrouter.api.route

import club.fdawei.nrouter.api.base.TypeBundle
import kotlin.reflect.KClass

/**
 * Created by david on 2019/05/27.
 */
class RouteNodeMeta(
    val path: String,
    val target: KClass<out Any>,
    val flags: Long,
    val desc: String,
    val handlerBundle: TypeBundle<RouteHandler>
) {
    val nodeInfo: RouteNodeInfo by lazy { RouteNodeInfo(target, flags) }

    override fun toString(): String {
        return "$desc{" +
                "path='$path', " +
                "target=${target.qualifiedName}, " +
                "flags=$flags, " +
                "handler=${handlerBundle.type.qualifiedName}" +
                "}"
    }
}