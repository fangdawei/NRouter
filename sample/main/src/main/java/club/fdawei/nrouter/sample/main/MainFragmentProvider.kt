package club.fdawei.nrouter.sample.main

import club.fdawei.nrouter.annotation.Provider
import club.fdawei.nrouter.api.action.ActionBundle
import club.fdawei.nrouter.api.component.fragment.FragmentAutowiredProvider
import kotlin.reflect.KClass

/**
 * Create by david on 2019/07/14.
 */
@Provider(MainFragment::class)
class MainFragmentProvider : FragmentAutowiredProvider() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : Any> getAutowired(
        source: Any,
        name: String,
        path: String,
        type: KClass<T>,
        data: ActionBundle
    ): T? {
        if (source !is MainFragment) {
            return null
        }
        if ("name" == name && type == String::class) {
            return "MainFragment" as T
        }
        return super.getAutowired(source, name, path, type, data)
    }
}