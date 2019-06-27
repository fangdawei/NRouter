package club.fdawei.mourouter.api.component.instance

import android.os.Bundle

/**
 * Created by david on 2019/06/04.
 */
class InitContext private constructor(
    val flags: Int,
    val extras: Bundle
) {
    companion object {

        @JvmStatic
        fun of(flags: Int, extras: Bundle): InitContext {
            return InitContext(flags, Bundle().apply {
                putAll(extras)
            })
        }
    }
}