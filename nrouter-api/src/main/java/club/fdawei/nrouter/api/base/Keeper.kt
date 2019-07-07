package club.fdawei.nrouter.api.base

/**
 * Created by david on 2019/07/04.
 */
class Keeper<T> private constructor(
    private val creator: () -> T
) {
    val instance by lazy { creator.invoke() }

    companion object {
        fun <T : Any> of(creator: () -> T): Keeper<T> {
            return Keeper(creator)
        }
    }
}