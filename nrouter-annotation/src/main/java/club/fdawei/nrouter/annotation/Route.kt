package club.fdawei.nrouter.annotation

import kotlin.reflect.KClass

/**
 * Created by david on 2019/05/24.
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.BINARY)
annotation class Route(
    val path: String,
    val flags: Long = 0,
    val desc: String = "",
    val handler: KClass<out Any> = Any::class
)