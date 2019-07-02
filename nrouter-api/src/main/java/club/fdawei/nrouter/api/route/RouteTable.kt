package club.fdawei.nrouter.api.route

import club.fdawei.nrouter.api.util.ExceptionUtils
import club.fdawei.nrouter.api.util.splitRoutePath

/**
 * Created by david on 2019/05/29.
 */
class RouteTable : RouteTree("", "") {

    fun registerRouteNode(routeNodeMetaData: RouteNodeMetaData) {
        if (routeNodeMetaData.path.isEmpty()) {
            ExceptionUtils.exception("Path illegal, $routeNodeMetaData")
            return
        }
        val pathSplitList = routeNodeMetaData.path.splitRoutePath()
        addHandler(pathSplitList, routeNodeMetaData)
    }

    fun registerInterceptor(interceptor: InterceptorMetaData) {
        val pathSplitList = interceptor.interceptPath.splitRoutePath()
        addInterceptor(pathSplitList, interceptor)
    }

    fun addressing(path: String): RouteResult {
        val pathSplitList = path.splitRoutePath()
        val interceptorList = mutableListOf<InterceptorMetaData>()
        val routeMetaData = addressing(pathSplitList, interceptorList)
        return RouteResult(routeMetaData, interceptorList)
    }

    fun clear() {
        childTrees.clear()
    }
}