package club.fdawei.nrouter.sample.base.service

import club.fdawei.nrouter.annotation.Provider
import club.fdawei.nrouter.api.action.ActionData
import club.fdawei.nrouter.api.inject.AutowiredProvider
import kotlin.reflect.KClass

/**
 * Created by david on 2019/06/06.
 */
@Provider(CustomSource::class, CustomSource2::class)
class CustomSourceProvider : AutowiredProvider() {
    override fun <T : Any> getAutowired(
        source: Any,
        name: String,
        type: KClass<T>,
        data: ActionData
    ): T? {
        return super.getAutowired(source, name, type, data)
    }
}