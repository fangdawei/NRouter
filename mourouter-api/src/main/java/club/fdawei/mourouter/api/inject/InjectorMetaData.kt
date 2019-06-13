package club.fdawei.mourouter.api.inject

import kotlin.reflect.KClass

/**
 * Create by david on 2019/06/07.
 */
class InjectorMetaData(
    val target: KClass<out Any>,
    val creator: () -> Injector
)