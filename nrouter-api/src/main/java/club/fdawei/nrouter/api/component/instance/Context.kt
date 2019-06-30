package club.fdawei.nrouter.api.component.instance

import android.os.Bundle

/**
 * Created by david on 2019/06/04.
 */
class Context private constructor(
    val flags: Int,
    val extras: Bundle
) {
    companion object {

        @JvmStatic
        fun of(flags: Int, extras: Bundle): Context {
            return Context(flags, Bundle().apply {
                putAll(extras)
            })
        }
    }
}