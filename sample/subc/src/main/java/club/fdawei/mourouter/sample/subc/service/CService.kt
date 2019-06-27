package club.fdawei.mourouter.sample.subc.service

import android.util.Log
import club.fdawei.mourouter.annotation.Route
import club.fdawei.mourouter.api.component.instance.Creatable
import club.fdawei.mourouter.sample.base.service.IService

/**
 * Created by david on 2019/06/06.
 */
@Route(path = "/subc/service/cservice")
class CService : IService, Creatable {
    override fun printName() {
        Log.i("CService", "my name is CService@${hashCode()}")
    }
}