package club.fdawei.mourouter.api.base

import kotlin.reflect.KClass

/**
 * Create by david on 2019/05/26.
 */
class DataContainer {

    private val datas = linkedMapOf<KClass<out Any>, Any>()

    fun put(value: Any) {
        datas[value::class] = value
    }

    fun putAll(other: DataContainer) {
        other.datas.forEach { (_, value) ->
            put(value)
        }
    }

    @Suppress("UNCHECKED_CAST")
    fun <T : Any> get(kClass: KClass<T>, assignable: Boolean = false): T? {
        val env = datas[kClass]
        return if (env != null) {
            env as T
        } else {
            if (assignable) {
                for (value in datas.values) {
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
            val iterator = datas.iterator()
            while (iterator.hasNext()) {
                val entry = iterator.next()
                if (kClass.isInstance(entry.value)) {
                    iterator.remove()
                }
            }
        } else {
            datas.remove(kClass)
        }
    }

    fun clear() {
        datas.clear()
    }
}