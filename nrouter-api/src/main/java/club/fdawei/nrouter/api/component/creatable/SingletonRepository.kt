package club.fdawei.nrouter.api.component.creatable

import kotlin.reflect.KClass

/**
 * Created by david on 2019/05/27.
 */
object SingletonRepository {

    private val instanceMap = mutableMapOf<KClass<out Any>, Any>()

    fun get(kClass: KClass<out Any>): Any? {
        return instanceMap[kClass]
    }

    fun getOrCreate(kClass: KClass<out Any>): Any? {
        return get(kClass) ?: synchronized(instanceMap) {
            get(kClass) ?: CreatableFactory.create(kClass).apply {
                if (this != null) {
                    put(kClass, this)
                }
            }
        }
    }

    private fun put(kClass: KClass<out Any>, instance: Any) {
        instanceMap[kClass] = instance
    }
}