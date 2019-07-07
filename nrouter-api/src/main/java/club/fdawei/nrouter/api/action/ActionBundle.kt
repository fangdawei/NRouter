package club.fdawei.nrouter.api.action

import android.os.Bundle
import club.fdawei.nrouter.api.base.TypeDataContainer

/**
 * Created by david on 2019/06/05.
 */
interface ActionBundle {
    var flags: Int
    val extras: Bundle
    val args: TypeDataContainer
}