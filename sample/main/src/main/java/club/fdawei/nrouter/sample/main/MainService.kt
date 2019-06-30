package club.fdawei.nrouter.sample.main

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import club.fdawei.nrouter.annotation.Route
import club.fdawei.nrouter.sample.base.service.ServiceBinder

@Route(path = "/main/service/main")
class MainService : Service() {

    private val binder: ServiceBinder by lazy {
        object : ServiceBinder() {
            override fun printName() {
                Log.i("MainService", "i am MainService")
            }
        }
    }

    override fun onCreate() {
        super.onCreate()
        Log.i("MainService", "onCreate")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.i("MainService", "onStartCommand")
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("MainService", "onDestroy")
    }

    override fun onBind(intent: Intent): IBinder {
        Log.i("MainService", "onBind")
        return binder
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Log.i("MainService", "onUnbind")
        return super.onUnbind(intent)
    }
}
