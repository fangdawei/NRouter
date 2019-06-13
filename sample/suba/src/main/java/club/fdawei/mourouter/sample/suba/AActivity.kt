package club.fdawei.mourouter.sample.suba

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import club.fdawei.mourouter.annotation.Route
import club.fdawei.mourouter.api.MouRouter
import kotlinx.android.synthetic.main.activity_a.*

@Route(path = "/suba/page/home")
class AActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_a)

        tv_mainpage_btn.setOnClickListener {
            MouRouter.route("/main/page/home").go()
        }

        tv_bpage_btn.setOnClickListener {
            MouRouter.route("/subb/page/home").go()
        }

        tv_cpage_btn.setOnClickListener {
            MouRouter.route("/subc/page/home").go()
        }
    }
}
