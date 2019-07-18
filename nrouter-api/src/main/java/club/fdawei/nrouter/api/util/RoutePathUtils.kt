@file:JvmName("RoutePathUtils")

package club.fdawei.nrouter.api.util

import android.net.Uri

/**
 * Create by david on 2019/05/26.
 */
const val ROUTE_PATH_SEPARATOR = '/'
const val ROUTE_PATH_ARG_SEPARATOR = '?'
const val ROUTE_ARG_SEPARATOR = '&'
const val ROUTE_ARG_KV_SEPARATOR = '='

fun String.parseRoutePath(): String {
    val index = this.indexOf(ROUTE_PATH_ARG_SEPARATOR)
    return if (index >= 0 && index < this.length) this.substring(0, index) else this
}

fun String.parseRouteArgs(): Map<String, String> {
    val argMap = mutableMapOf<String, String>()
    val index = this.indexOf(ROUTE_PATH_ARG_SEPARATOR)
    if (index >= 0 && index < this.length) {
        this.substring(index + 1).split(ROUTE_ARG_SEPARATOR).forEach {
            val arg = it.split(ROUTE_ARG_KV_SEPARATOR, ignoreCase = false, limit = 2)
            when {
                arg.size > 1 -> {
                    argMap[arg[0]] = arg[1]
                }
                arg.isNotEmpty() -> {
                    argMap[arg[0]] = ""
                }
            }
        }
    }
    return argMap
}

fun String.trimRoutePath(): String {
    var realPath = this.parseRoutePath()
    if (realPath.isNotEmpty() && realPath.startsWith(ROUTE_PATH_SEPARATOR)) {
        realPath = realPath.substring(1)
    }
    if (realPath.isNotEmpty() && realPath.endsWith(ROUTE_PATH_SEPARATOR)) {
        realPath = realPath.substring(0, realPath.length - 1)
    }
    return realPath
}

fun String.splitRoutePath(): List<String> {
    val realPath = this.trimRoutePath()
    return if (realPath.isEmpty()) emptyList() else realPath.split(ROUTE_PATH_SEPARATOR)
}

fun Uri?.routeUri(): String? {
    return when {
        this == null -> null
        this.path.isNullOrBlank() -> null
        this.query.isNullOrBlank() -> this.path
        else -> this.path!! + ROUTE_PATH_ARG_SEPARATOR + this.query
    }
}