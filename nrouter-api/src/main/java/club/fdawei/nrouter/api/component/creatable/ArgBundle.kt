package club.fdawei.nrouter.api.component.creatable

import android.os.Bundle

/**
 * Created by david on 2019/06/04.
 */
class ArgBundle private constructor(
    val flags: Int,
    val extras: Bundle
) {
    companion object {
        @JvmStatic
        fun of(flags: Int, extras: Bundle): ArgBundle {
            return ArgBundle(flags, Bundle().apply {
                putAll(extras)
            })
        }
    }
}