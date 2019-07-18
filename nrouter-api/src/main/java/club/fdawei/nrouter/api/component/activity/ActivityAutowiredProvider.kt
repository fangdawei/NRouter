package club.fdawei.nrouter.api.component.activity

import android.app.Activity
import club.fdawei.nrouter.api.action.ActionBundle
import club.fdawei.nrouter.api.inject.AutowiredProvider
import kotlin.reflect.KClass

/**
 * Create by david on 2019/06/04.
 */
open class ActivityAutowiredProvider : AutowiredProvider() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : Any> getAutowired(
        source: Any,
        name: String,
        path: String,
        type: KClass<T>,
        data: ActionBundle
    ): T? {
        if (source !is Activity) {
            return null
        }
        var value: T? = null
        if (name.isNotBlank()) {
            val extra = source.intent.extras?.get(name)
            if (type.isInstance(extra)) {
                value = extra as T
            }
        }
        if (value == null) {
            value = super.getAutowired(source, name, path, type, data)
        }
        return value
    }
}