package club.fdawei.nrouter.api.component.activity.arg

import club.fdawei.nrouter.api.base.DataValue

/**
 * Create by david on 2019/06/02.
 */
class RequestCode private constructor(value: Int) : DataValue<Int>(value) {
    companion object {
        @JvmStatic
        fun of(value: Int): RequestCode {
            return RequestCode(value)
        }
    }
}