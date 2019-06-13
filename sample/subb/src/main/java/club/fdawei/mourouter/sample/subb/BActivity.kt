package club.fdawei.mourouter.sample.subb

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import club.fdawei.mourouter.annotation.Route
import club.fdawei.mourouter.api.MouRouter
import kotlinx.android.synthetic.main.activity_b.*

@Route(path = "/subb/page/home")
class BActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_b)

        tv_mainpage_btn.setOnClickListener {
            MouRouter.route("/main/page/home").go()
        }

        tv_apage_btn.setOnClickListener {
            MouRouter.route("/suba/page/home").go()
        }

        tv_cpage_btn.setOnClickListener {
            MouRouter.route("/subc/page/home").go()
        }
    }
}
