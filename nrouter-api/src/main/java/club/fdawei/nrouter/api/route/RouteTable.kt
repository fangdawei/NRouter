package club.fdawei.nrouter.api.route

import club.fdawei.nrouter.api.registry.RouteRegistry
import club.fdawei.nrouter.api.util.safeThrowException
import club.fdawei.nrouter.api.util.splitRoutePath
import java.util.concurrent.locks.ReentrantReadWriteLock
import kotlin.concurrent.read
import kotlin.concurrent.write

/**
 * Created by david on 2019/05/29.
 */
class RouteTable : RouteTree("", ""), RouteRegistry {

    private val lock = ReentrantReadWriteLock()

    override fun register(meta: RouteNodeMeta) {
        if (meta.path.isBlank()) {
            safeThrowException("Path illegal, $meta")
            return
        }
        val pathSplitList = meta.path.splitRoutePath()
        lock.write {
            addHandler(pathSplitList, meta)
        }
    }

    override fun register(meta: InterceptorMeta) {
        val pathSplitList = meta.interceptPath.splitRoutePath()
        lock.write {
            addInterceptor(pathSplitList, meta)
        }
    }

    fun addressing(path: String): AddressingResult {
        val pathSplitList = path.splitRoutePath()
        val interceptorMetaList = mutableListOf<InterceptorMeta>()
        val routeNodeMeta = lock.read {
            addressing(pathSplitList, interceptorMetaList)
        }
        val routeHandler = if (routeNodeMeta == null) {
            null
        } else {
            RouteHandlerRepository.get(routeNodeMeta.handlerBundle)
        }
        val interceptorList = interceptorMetaList.map { InterceptorRepository.get(it.typeBundle) }
        return AddressingResult(routeNodeMeta, routeHandler, interceptorList)
    }

    fun clear() {
        lock.write {
            childTrees.clear()
        }
    }
}