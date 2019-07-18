package club.fdawei.nrouter.sample.suba

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import club.fdawei.nrouter.annotation.Route
import club.fdawei.nrouter.api.NRouter
import club.fdawei.nrouter.api.component.service.arg.ServiceOption
import club.fdawei.nrouter.sample.base.IPageLogger
import kotlinx.android.synthetic.main.activity_a.*

@Route(path = "/suba/page/home", desc = "A页面")
class AActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_a)

        tvMainPageBtn.setOnClickListener {
            NRouter.route("/main/page/home").go()
        }

        tvBPageBtn.setOnClickListener {
            NRouter.route("/subb/page/home").go()
        }

        tvCPageBtn.setOnClickListener {
            NRouter.route("/subc/page/home").withString("from", "AActivity").go()
        }

        tvStartMainService.setOnClickListener {
            NRouter.route("/main/service/main").arg(ServiceOption.START).go()
        }

        tvStopMainService.setOnClickListener {
            NRouter.route("/main/service/main").arg(ServiceOption.STOP).go()
        }

        NRouter.instance().get(IPageLogger::class)?.logPage("AActivity")
    }
}
