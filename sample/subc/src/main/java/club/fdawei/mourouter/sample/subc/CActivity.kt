package club.fdawei.mourouter.sample.subc

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import club.fdawei.mourouter.annotation.Route
import club.fdawei.mourouter.api.MouRouter
import kotlinx.android.synthetic.main.activity_c.*

@Route(path = "/subc/page/home")
class CActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_c)

        tv_mainpage_btn.setOnClickListener {
            MouRouter.route("/main/page/home").go()
        }

        tv_apage_btn.setOnClickListener {
            MouRouter.route("/suba/page/home").go()
        }

        tv_bpage_btn.setOnClickListener {
            MouRouter.route("/subb/page/home").go()
        }
    }
}
