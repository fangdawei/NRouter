package club.fdawei.nrouter.api.component.activity.arg

import android.os.Bundle
import club.fdawei.nrouter.api.base.DataValue

/**
 * Created by david on 2019/07/13.
 */
class OptionsBundle private constructor(value: Bundle) : DataValue<Bundle>(value) {
    companion object {
        @JvmStatic
        fun of(bundle: Bundle): OptionsBundle {
            return OptionsBundle(bundle)
        }
    }
}