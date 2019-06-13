package club.fdawei.mourouter.api.component.instance

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
        var instance = get(kClass)
        if (instance == null) {
            synchronized(this) {
                instance = get(kClass)
                if (instance == null) {
                    val instanceCreated = InstanceFactory.create(kClass)
                    if (instanceCreated != null) {
                        put(kClass, instanceCreated)
                    }
                    instance = instanceCreated
                }
            }
        }
        return instance
    }

    private fun put(kClass: KClass<out Any>, instance: Any) {
        instanceMap[kClass] = instance
    }
}