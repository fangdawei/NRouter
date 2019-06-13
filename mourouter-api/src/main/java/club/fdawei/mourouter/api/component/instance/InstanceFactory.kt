package club.fdawei.mourouter.api.component.instance

import club.fdawei.mourouter.api.util.ExceptionUtils
import kotlin.reflect.KClass

/**
 * Create by david on 2019/06/02.
 */
object InstanceFactory {
    fun create(kClass: KClass<out Any>): Any? {
        return try {
            kClass.java.newInstance()
        } catch (e: Exception) {
            ExceptionUtils.exception(
                "create instance of ${kClass.qualifiedName} error, ${e.message}"
            )
            null
        }
    }
}