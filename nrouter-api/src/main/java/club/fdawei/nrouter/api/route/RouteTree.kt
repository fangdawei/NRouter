package club.fdawei.nrouter.api.route

import club.fdawei.nrouter.api.util.ROUTE_PATH_SEPARATOR
import club.fdawei.nrouter.api.util.throwException
import java.util.*

/**
 * Created by david on 2019/05/29.
 */
open class RouteTree(
    val prePath: String,
    val key: String
) {
    protected val childPrePath: String by lazy {
        if (prePath.endsWith(ROUTE_PATH_SEPARATOR)) "$prePath$key"
        else "$prePath$ROUTE_PATH_SEPARATOR$key"
    }
    protected var routeNodeMeta: RouteNodeMeta? = null
    protected val interceptors = LinkedList<InterceptorMeta>()
    protected val childTrees: MutableMap<String, RouteTree> by lazy { mutableMapOf<String, RouteTree>() }

    private fun getOrCreateChildTree(key: String): RouteTree {
        var childTree = childTrees[key]
        return if (childTree != null) {
            childTree
        } else {
            childTree = RouteTree(childPrePath, key)
            childTrees[key] = childTree
            childTree
        }
    }

    fun addHandler(path: List<String>, routeNodeMeta: RouteNodeMeta) {
        if (path.isEmpty()) {
            if (this.routeNodeMeta != null) {
                throwException(
                    "${this.routeNodeMeta!!.target.qualifiedName} and " +
                            "${routeNodeMeta.target.qualifiedName} with same path"
                )
            }
            this.routeNodeMeta = routeNodeMeta
        } else {
            val childKey = path[0]
            val childTree = getOrCreateChildTree(childKey)
            childTree.addHandler(path.subList(1, path.size), routeNodeMeta)
        }
    }

    fun addInterceptor(path: List<String>, interceptor: InterceptorMeta) {
        if (path.isEmpty()) {
            interceptors.add(interceptor)
        } else {
            val childKey = path[0]
            val childTree = getOrCreateChildTree(childKey)
            childTree.addInterceptor(path.subList(1, path.size), interceptor)
        }
    }

    fun addressing(path: List<String>, interceptors: MutableList<InterceptorMeta>): RouteNodeMeta? {
        val result = if (path.isEmpty()) {
            routeNodeMeta
        } else {
            childTrees[path[0]]?.addressing(path.subList(1, path.size), interceptors)
        }
        interceptors.addAll(this.interceptors)
        return result
    }

    open fun print(): String {
        val builder = StringBuilder()
        if (routeNodeMeta != null || interceptors.isNotEmpty()) {
            builder.append("\n$childPrePath -> $routeNodeMeta; interceptors=$interceptors")
        }
        childTrees.forEach { (_, data) ->
            builder.append(data.print())
        }
        return builder.toString()
    }
}