package club.fdawei.mourouter.api.route

import club.fdawei.mourouter.api.util.ExceptionUtils
import club.fdawei.mourouter.api.util.ROUTE_PATH_SEPARATOR
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
    protected var handlerMetaData: HandlerMetaData? = null
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

    fun addHandler(path: List<String>, handlerMetaData: HandlerMetaData) {
        if (path.isEmpty()) {
            if (this.handlerMetaData != null) {
                ExceptionUtils.exception(
                    "${this.handlerMetaData!!.target.qualifiedName} and " +
                            "${handlerMetaData.target.qualifiedName} with same path"
                )
            }
            this.handlerMetaData = handlerMetaData
        } else {
            val childKey = path[0]
            val childTree = getOrCreateChildTree(childKey)
            childTree.addHandler(path.subList(1, path.size), handlerMetaData)
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

    fun addressing(path: List<String>, interceptors: MutableList<InterceptorMetaData>): HandlerMetaData? {
        val result = if (path.isEmpty()) {
            handlerMetaData
        } else {
            childTrees[path[0]]?.addressing(path.subList(1, path.size), interceptors)
        }
        interceptors.addAll(this.interceptors)
        return result
    }

    open fun print(): String {
        val builder = StringBuilder()
        if (handlerMetaData != null || interceptors.isNotEmpty()) {
            builder.append("\n$childPrePath -> handler=$handlerMetaData; interceptors=$interceptors")
        }
        childTrees.forEach { (_, data) ->
            builder.append(data.print())
        }
        return builder.toString()
    }
}