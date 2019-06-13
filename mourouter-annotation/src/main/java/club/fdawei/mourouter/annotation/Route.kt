package club.fdawei.mourouter.annotation

import kotlin.reflect.KClass

/**
 * Created by david on 2019/05/24.
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.BINARY)
annotation class Route(
    val path: String,
    val flags: Int = 0,
    val handler: KClass<out Any> = Any::class
)