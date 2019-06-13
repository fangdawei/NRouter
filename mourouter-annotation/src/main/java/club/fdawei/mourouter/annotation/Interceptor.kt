package club.fdawei.mourouter.annotation

/**
 * Created by david on 2019/05/29.
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.BINARY)
annotation class Interceptor(
    val path: String = "",
    val priority: Int = DEFAULT_PRIORITY
) {
    companion object {
        const val DEFAULT_PRIORITY = 9999
    }
}