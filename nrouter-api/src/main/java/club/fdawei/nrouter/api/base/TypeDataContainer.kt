package club.fdawei.nrouter.api.base

import kotlin.reflect.KClass

/**
 * Create by david on 2019/05/26.
 */
class TypeDataContainer {

    private val dataMap = linkedMapOf<KClass<out Any>, Any>()

    fun put(value: Any) {
        dataMap[value::class] = value
    }

    fun putAll(other: TypeDataContainer) {
        other.dataMap.forEach { (_, value) ->
            put(value)
        }
    }

    @Suppress("UNCHECKED_CAST")
    fun <T : Any> get(kClass: KClass<T>, assignable: Boolean = false): T? {
        val env = dataMap[kClass]
        return if (env != null) {
            env as T
        } else {
            if (assignable) {
                for (value in dataMap.values) {
                    if (kClass.isInstance(value)) {
                        return value as T
                    }
                }
                null
            } else {
                null
            }
        }
    }

    fun remove(kClass: KClass<Any>, assignable: Boolean = false) {
        if (assignable) {
            val iterator = dataMap.iterator()
            while (iterator.hasNext()) {
                val entry = iterator.next()
                if (kClass.isInstance(entry.value)) {
                    iterator.remove()
                }
            }
        } else {
            dataMap.remove(kClass)
        }
    }

    fun clear() {
        dataMap.clear()
    }
}