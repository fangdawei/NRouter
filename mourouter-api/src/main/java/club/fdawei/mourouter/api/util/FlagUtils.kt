package club.fdawei.mourouter.api.util

/**
 * Created by david on 2019/06/05.
 */
const val FLAG_NULL = 0x00000000

fun Int.withFlag(flag: Int): Boolean {
    return this and flag != FLAG_NULL
}