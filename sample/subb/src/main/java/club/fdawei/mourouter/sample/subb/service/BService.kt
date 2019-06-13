package club.fdawei.mourouter.sample.subb.service

import android.util.Log
import club.fdawei.mourouter.annotation.Route
import club.fdawei.mourouter.api.component.instance.FLAG_SINGLETON
import club.fdawei.mourouter.api.component.instance.Instantiable
import club.fdawei.mourouter.sample.base.service.IService

/**
 * Create by david on 2019/06/02.
 */
@Route(path = "/subb/service/bservice", flags = FLAG_SINGLETON)
class BService : IService, Instantiable {
    override fun printName() {
        Log.i("BService", "my name is BService@${hashCode()}")
    }
}