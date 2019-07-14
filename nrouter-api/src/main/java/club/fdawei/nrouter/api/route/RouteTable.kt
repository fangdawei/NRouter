package club.fdawei.nrouter.api.route

import club.fdawei.nrouter.api.util.splitRoutePath
import club.fdawei.nrouter.api.util.throwException

/**
 * Created by david on 2019/05/29.
 */
class RouteTable : RouteTree("", ""), RouteRegistry {

    override fun registerRouteNode(routeNodeMeta: RouteNodeMeta) {
        if (routeNodeMeta.path.isEmpty()) {
            throwException("Path illegal, $routeNodeMeta")
            return
        }
        val pathSplitList = routeNodeMeta.path.splitRoutePath()
        addHandler(pathSplitList, routeNodeMeta)
        RouteHandlerRepository.register(routeNodeMeta.handlerBundle)
    }

    override fun registerInterceptor(interceptor: InterceptorMeta) {
        val pathSplitList = interceptor.interceptPath.splitRoutePath()
        addInterceptor(pathSplitList, interceptor)
    }

    fun addressing(path: String): AddressingResult {
        val pathSplitList = path.splitRoutePath()
        val interceptorList = mutableListOf<InterceptorMeta>()
        val routeNodeMeta = addressing(pathSplitList, interceptorList)
        val routeHandler = if (routeNodeMeta == null) {
            null
        } else {
            RouteHandlerRepository.get(routeNodeMeta.handlerBundle.type)
        }
        return AddressingResult(routeNodeMeta, routeHandler, interceptorList)
    }

    fun clear() {
        childTrees.clear()
    }
}