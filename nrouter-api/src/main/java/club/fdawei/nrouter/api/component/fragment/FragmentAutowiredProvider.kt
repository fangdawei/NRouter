package club.fdawei.nrouter.api.component.fragment

import android.os.Bundle
import club.fdawei.nrouter.api.action.ActionBundle
import club.fdawei.nrouter.api.inject.AutowiredProvider
import kotlin.reflect.KClass

/**
 * Created by david on 2019/06/04.
 */
class FragmentAutowiredProvider : AutowiredProvider() {
    @Suppress("UNCHECKED_CAST", "DEPRECATION")
    override fun <T : Any> getAutowired(
        source: Any,
        name: String,
        type: KClass<T>,
        data: ActionBundle
    ): T? {
        var arguments: Bundle? = null
        val isFragment = when (source) {
            is android.app.Fragment -> {
                arguments = source.arguments
                true
            }
            is android.support.v4.app.Fragment -> {
                arguments = source.arguments
                true
            }
            else -> {
                false
            }
        }
        if (!isFragment) {
            return null
        }
        var value: T? = null
        val extra = arguments?.get(name)
        if (type.isInstance(extra)) {
            value = extra as T
        }
        if (value == null) {
            value = super.getAutowired(source, name, type, data)
        }
        return value
    }
}