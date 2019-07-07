package club.fdawei.nrouter.sample.subb.creatable

import android.util.Log
import club.fdawei.nrouter.annotation.Route
import club.fdawei.nrouter.api.component.creatable.Creatable
import club.fdawei.nrouter.api.component.creatable.FLAG_SINGLETON
import club.fdawei.nrouter.sample.base.creatable.IService

/**
 * Create by david on 2019/06/02.
 */
@Route(path = "/subb/service/bservice", flags = FLAG_SINGLETON)
class BService : IService, Creatable {
    override fun printName() {
        Log.i("BService", "my name is BService@${hashCode()}")
    }
}