package club.fdawei.nrouter.api.route

import club.fdawei.nrouter.api.util.ExceptionUtils
import club.fdawei.nrouter.api.util.ROUTE_PATH_SEPARATOR
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
    protected var routeNodeMetaData: RouteNodeMetaData? = null
    protected val interceptors = LinkedList<InterceptorMetaData>()
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

    fun addHandler(path: List<String>, routeNodeMetaData: RouteNodeMetaData) {
        if (path.isEmpty()) {
            if (this.routeNodeMetaData != null) {
                ExceptionUtils.exception(
                    "${this.routeNodeMetaData!!.target.qualifiedName} and " +
                            "${routeNodeMetaData.target.qualifiedName} with same path"
                )
            }
            this.routeNodeMetaData = routeNodeMetaData
        } else {
            val childKey = path[0]
            val childTree = getOrCreateChildTree(childKey)
            childTree.addHandler(path.subList(1, path.size), routeNodeMetaData)
        }
    }

    fun addInterceptor(path: List<String>, interceptor: InterceptorMetaData) {
        if (path.isEmpty()) {
            interceptors.add(interceptor)
        } else {
            val childKey = path[0]
            val childTree = getOrCreateChildTree(childKey)
            childTree.addInterceptor(path.subList(1, path.size), interceptor)
        }
    }

    fun addressing(path: List<String>, interceptors: MutableList<InterceptorMetaData>): RouteNodeMetaData? {
        val result = if (path.isEmpty()) {
            routeNodeMetaData
        } else {
            childTrees[path[0]]?.addressing(path.subList(1, path.size), interceptors)
        }
        interceptors.addAll(this.interceptors)
        return result
    }

    open fun print(): String {
        val builder = StringBuilder()
        if (routeNodeMetaData != null || interceptors.isNotEmpty()) {
            builder.append("\n$childPrePath -> $routeNodeMetaData; interceptors=$interceptors")
        }
        childTrees.forEach { (_, data) ->
            builder.append(data.print())
        }
        return builder.toString()
    }
}