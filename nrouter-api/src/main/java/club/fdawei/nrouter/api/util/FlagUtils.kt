@file:JvmName("FlagUtils")

package club.fdawei.nrouter.api.util

/**
 * Created by david on 2019/06/05.
 */
private const val LONG_FLAG_NULL = 0x00L

fun Long.withFlag(flag: Long): Boolean {
    return this and flag != LONG_FLAG_NULL
}

fun Long.notWithFlag(flag: Long): Boolean {
    return !this.withFlag(flag)
}

private const val INT_FLAG_NULL = 0x00

fun Int.withFlag(flag: Int): Boolean {
    return this and flag != INT_FLAG_NULL
}

fun Int.notWithFlag(flag: Int): Boolean {
    return !this.withFlag(flag)
}