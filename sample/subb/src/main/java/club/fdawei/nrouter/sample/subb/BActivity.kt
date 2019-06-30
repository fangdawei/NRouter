package club.fdawei.nrouter.sample.subb

import android.content.ComponentName
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.support.v7.app.AppCompatActivity
import club.fdawei.nrouter.annotation.Route
import club.fdawei.nrouter.api.NRouter
import club.fdawei.nrouter.api.component.service.ServiceOption
import club.fdawei.nrouter.sample.base.service.ServiceBinder
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
            NRouter.route("/main/page/home").go()
        }

        tvAPageBtn.setOnClickListener {
            NRouter.route("/suba/page/home").go()
        }

        tvCPageBtn.setOnClickListener {
            NRouter.route("/subc/page/home").go()
        }

        tvMainServiceBtn.setOnClickListener {
            srvBinder?.printName()
        }
    }

    override fun onStart() {
        super.onStart()
        NRouter.route("/main/service/main")
            .env(ServiceOption.BIND)
            .env(conn)
            .go()
    }

    override fun onStop() {
        super.onStop()
        NRouter.route("/main/service/main")
            .env(ServiceOption.UNBIND)
            .env(conn)
            .go()
    }
}
