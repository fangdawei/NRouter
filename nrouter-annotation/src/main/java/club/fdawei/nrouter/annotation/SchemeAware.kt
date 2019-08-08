package club.fdawei.nrouter.annotation

/**
 * Create by david on 2019/07/06.
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.BINARY)
annotation class SchemeAware(
    val priority: Int = DEFAULT_PRIORITY
) {
    companion object {
        const val DEFAULT_PRIORITY = 9999
    }
}