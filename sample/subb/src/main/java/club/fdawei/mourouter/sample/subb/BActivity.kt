package club.fdawei.mourouter.sample.subb

import android.content.ComponentName
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.support.v7.app.AppCompatActivity
import club.fdawei.mourouter.annotation.Route
import club.fdawei.mourouter.api.MouRouter
import club.fdawei.mourouter.api.component.service.ServiceOption
import club.fdawei.mourouter.sample.base.service.ServiceBinder
import kotlinx.android.synthetic.main.activity_b.*

@Route(path = "/subb/page/home")
class BActivity : AppCompatActivity() {

    private val conn = object : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName?) {
            srvBinder = null
        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            srvBinder = service as ServiceBinder
        }

    }
    private var srvBinder: ServiceBinder? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_b)

        tvMainPageBtn.setOnClickListener {
            MouRouter.route("/main/page/home").go()
        }

        tvAPageBtn.setOnClickListener {
            MouRouter.route("/suba/page/home").go()
        }

        tvCPageBtn.setOnClickListener {
            MouRouter.route("/subc/page/home").go()
        }

        tvMainServiceBtn.setOnClickListener {
            srvBinder?.printName()
        }
    }

    override fun onStart() {
        super.onStart()
        MouRouter.route("/main/service/main")
            .env(ServiceOption.BIND)
            .env(conn)
            .go()
    }

    override fun onStop() {
        super.onStop()
        MouRouter.route("/main/service/main")
            .env(ServiceOption.UNBIND)
            .env(conn)
            .go()
    }
}
