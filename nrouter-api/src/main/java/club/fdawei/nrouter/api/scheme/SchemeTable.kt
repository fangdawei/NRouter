package club.fdawei.nrouter.api.scheme

import club.fdawei.nrouter.api.registry.SchemeRegistry
import java.util.*
import java.util.concurrent.locks.ReentrantReadWriteLock
import kotlin.concurrent.read
import kotlin.concurrent.write

/**
 * Create by david on 2019/07/06.
 */
class SchemeTable : SchemeRegistry {

    private val schemeMetas = TreeSet<SchemeMeta>()
    private var schemeHandlers: List<SchemeHandler>? = null

    private val metasLock = ReentrantReadWriteLock()
    private val handlersLock = ReentrantReadWriteLock()

    override fun register(meta: SchemeMeta) {
        metasLock.write {
            schemeMetas.add(meta)
        }
        metasLock.write {
            schemeHandlers = null
        }
    }

    fun getSchemeHandlers(): List<SchemeHandler> {
        handlersLock.read {
            if (schemeHandlers != null) {
                return schemeHandlers!!
            }
        }
        handlersLock.write {
            if (schemeHandlers == null) {
                metasLock.read {
                    schemeHandlers = schemeMetas.map { it.handler }
                }
            }
            return schemeHandlers!!
        }
    }
}