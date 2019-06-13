package club.fdawei.mourouter.api.component.activity

import android.app.Activity
import club.fdawei.mourouter.api.action.ActionData
import club.fdawei.mourouter.api.inject.AutowiredProvider
import club.fdawei.mourouter.api.util.isRoutePath
import kotlin.reflect.KClass

/**
 * Create by david on 2019/06/04.
 */
class ActivityAutowiredProvider : AutowiredProvider() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : Any> getAutowired(source: Any, name: String, type: KClass<T>, data: ActionData): T? {
        if (source !is Activity) {
            return null
        }
        var value: T? = null
        if (!name.isRoutePath()) {
            val extra = source.intent.extras?.get(name)
            if (type.isInstance(extra)) {
                value = extra as T
            }
        }
        if (value == null) {
            value = super.getAutowired(source, name, type, data)
        }
        return value
    }
}