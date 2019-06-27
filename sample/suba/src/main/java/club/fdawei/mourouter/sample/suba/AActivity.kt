package club.fdawei.mourouter.sample.suba

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import club.fdawei.mourouter.annotation.Route
import club.fdawei.mourouter.api.MouRouter
import club.fdawei.mourouter.api.component.service.ServiceOption
import kotlinx.android.synthetic.main.activity_a.*

@Route(path = "/suba/page/home")
class AActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_a)

        tvMainPageBtn.setOnClickListener {
            MouRouter.route("/main/page/home").go()
        }

        tvBPageBtn.setOnClickListener {
            MouRouter.route("/subb/page/home").go()
        }

        tvCPageBtn.setOnClickListener {
            MouRouter.route("/subc/page/home").go()
        }

        tvStartMainService.setOnClickListener {
            MouRouter.route("/main/service/main").env(ServiceOption.START).go()
        }

        tvStopMainService.setOnClickListener {
            MouRouter.route("/main/service/main").env(ServiceOption.STOP).go()
        }
    }
}
