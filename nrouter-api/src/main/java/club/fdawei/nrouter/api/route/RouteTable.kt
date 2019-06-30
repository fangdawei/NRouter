package club.fdawei.nrouter.api.route

import club.fdawei.nrouter.api.util.ExceptionUtils
import club.fdawei.nrouter.api.util.splitRoutePath

/**
 * Created by david on 2019/05/29.
 */
class RouteTable : RouteTree("", "") {

    fun registerHandler(handlerMetaData: HandlerMetaData) {
        if (handlerMetaData.path.isEmpty()) {
            ExceptionUtils.exception("Path illegal, $handlerMetaData")
            return
        }
        val pathSplitList = handlerMetaData.path.splitRoutePath()
        addHandler(pathSplitList, handlerMetaData)
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