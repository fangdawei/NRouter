package club.fdawei.nrouter.sample.suba.service

import android.util.Log
import club.fdawei.nrouter.annotation.Route
import club.fdawei.nrouter.api.component.instance.Creatable
import club.fdawei.nrouter.sample.base.service.IService

/**
 * Created by david on 2019/05/30.
 */
@Route(path = "/suba/service/aservice")
class AService : IService, Creatable {
    override fun printName() {
        Log.i("AService", "my name is AService@${hashCode()}")
    }
}