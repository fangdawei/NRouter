package club.fdawei.nrouter.api.component.activity.arg

/**
 * Created by david on 2019/07/13.
 */
class PendingTransition private constructor(
    val enterAnim: Int,
    val exitAnim: Int
) {
    companion object {
        @JvmStatic
        fun of(enterAnim: Int, exitAnim: Int): PendingTransition {
            return PendingTransition(enterAnim, exitAnim)
        }
    }
}