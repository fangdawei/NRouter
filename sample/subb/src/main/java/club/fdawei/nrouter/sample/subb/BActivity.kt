package club.fdawei.nrouter.sample.subb

import android.content.ComponentName
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.support.v7.app.AppCompatActivity
import club.fdawei.nrouter.annotation.Route
import club.fdawei.nrouter.api.NRouter
import club.fdawei.nrouter.api.component.service.arg.ServiceOption
import club.fdawei.nrouter.sample.base.IPageLogger
import club.fdawei.nrouter.sample.base.ServiceBinder
import kotlinx.android.synthetic.main.activity_b.*

@Route(path = "/subb/page/home", desc = "B页面")
class BActivity : AppCompatActivity() {

    private val conn = object : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName?) {
            serviceBinder = null
        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            serviceBinder = service as ServiceBinder
        }
    }
    private var serviceBinder: ServiceBinder? = null

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
            NRouter.route("/subc/page/home").withString("from", "BActivity").go()
        }

        tvMainServiceBtn.setOnClickListener {
            serviceBinder?.printName()
        }

        NRouter.route("/common/page/logger").get(IPageLogger::class)?.logPage("BActivity")
    }

    override fun onStart() {
        super.onStart()
        NRouter.route("/main/service/main")
            .arg(ServiceOption.BIND)
            .arg(conn)
            .go()
    }

    override fun onStop() {
        super.onStop()
        NRouter.route("/main/service/main")
            .arg(ServiceOption.UNBIND)
            .arg(conn)
            .go()
    }
}
