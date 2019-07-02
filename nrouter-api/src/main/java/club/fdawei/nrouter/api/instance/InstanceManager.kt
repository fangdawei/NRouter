package club.fdawei.nrouter.api.instance

import club.fdawei.nrouter.api.action.InstanceAction
import java.util.concurrent.ConcurrentHashMap
import kotlin.reflect.KClass

/**
 * Created by david on 2019/07/02.
 */
class InstanceManager : InstanceAction {

    private val instanceMap: MutableMap<KClass<out Any>, Any> = ConcurrentHashMap()

    @Suppress("UNCHECKED_CAST")
    override fun <T : Any> get(clz: KClass<T>): T? {
        return instanceMap[clz]?.run {
            if (clz.isInstance(this)) this as T else null
        }
    }

    override fun register(clz: KClass<out Any>, instance: Any) {
        instanceMap[clz] = instance
    }
}