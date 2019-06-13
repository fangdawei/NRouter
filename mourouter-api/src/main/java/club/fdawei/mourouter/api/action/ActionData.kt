package club.fdawei.mourouter.api.action

import android.os.Bundle
import club.fdawei.mourouter.api.base.DataContainer

/**
 * Created by david on 2019/06/05.
 */
interface ActionData {
    var flags: Int
    val extras: Bundle
    val envs: DataContainer
}