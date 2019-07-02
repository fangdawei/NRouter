package club.fdawei.nrouter.api.component.creatable

import club.fdawei.nrouter.api.util.ExceptionUtils
import kotlin.reflect.KClass

/**
 * Create by david on 2019/06/02.
 */
object CreatableFactory {
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