package club.fdawei.nrouter.annotation

import kotlin.reflect.KClass

/**
 * Created by david on 2019/06/05.
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.BINARY)
annotation class Provider(
    vararg val sources: KClass<out Any>
)