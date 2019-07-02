package club.fdawei.nrouter.sample.subc.service

import android.util.Log
import club.fdawei.nrouter.annotation.Route
import club.fdawei.nrouter.api.component.creatable.Creatable
import club.fdawei.nrouter.sample.base.service.IService

/**
 * Created by david on 2019/06/06.
 */
@Route(path = "/subc/service/cservice")
class CService : IService, Creatable {
    override fun printName() {
        Log.i("CService", "my name is CService@${hashCode()}")
    }
}