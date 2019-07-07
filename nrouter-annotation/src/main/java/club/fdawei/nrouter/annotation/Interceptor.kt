package club.fdawei.nrouter.annotation

/**
 * Created by david on 2019/05/29.
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.BINARY)
annotation class Interceptor(
    val path: String = "",
    val priority: Int = DEFAULT_PRIORITY,
    val desc: String = ""
) {
    companion object {
        const val DEFAULT_PRIORITY = 9999
    }
}