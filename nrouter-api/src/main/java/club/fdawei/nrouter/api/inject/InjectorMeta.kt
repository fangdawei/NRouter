package club.fdawei.nrouter.api.inject

import club.fdawei.nrouter.api.base.TypeBundle
import kotlin.reflect.KClass

/**
 * Create by david on 2019/06/07.
 */
class InjectorMeta(
    val target: KClass<out Any>,
    val injectorBundle: TypeBundle<Injector>
)