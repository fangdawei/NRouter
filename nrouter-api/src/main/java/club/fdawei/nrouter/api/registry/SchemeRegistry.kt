package club.fdawei.nrouter.api.registry

import club.fdawei.nrouter.api.scheme.SchemeMeta

/**
 * Create by david on 2019/07/10.
 */
interface SchemeRegistry {
    fun register(meta: SchemeMeta)
}