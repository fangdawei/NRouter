package club.fdawei.nrouter.annotation

/**
 * Created by david on 2019/06/03.
 */
@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.BINARY)
annotation class Autowired(
    val name: String = ""
)