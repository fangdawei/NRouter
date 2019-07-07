package club.fdawei.nrouter.sample.main

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import club.fdawei.nrouter.annotation.Autowired
import club.fdawei.nrouter.annotation.Route
import club.fdawei.nrouter.api.NRouter
import club.fdawei.nrouter.api.component.activity.arg.ActivityOption
import club.fdawei.nrouter.api.component.activity.arg.RequestCode
import club.fdawei.nrouter.sample.base.IPageLogger
import club.fdawei.nrouter.sample.base.creatable.IService
import kotlinx.android.synthetic.main.activity_main.*

@Route(path = "/main/page/home", desc = "Home页面")
class MainActivity : AppCompatActivity() {

    @Autowired(name = "/subc/service/cservice")
    var cService: IService? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvAPageBtn.setOnClickListener {
            NRouter.route("/suba/page/home").arg(this).go()
        }

        tvBPageBtn.setOnClickListener {
            NRouter.route("/subb/page/home").arg(this).go()
        }

        tvCPageBtn.setOnClickListener {
            NRouter.route("/subc/page/home")
                .arg(this)
                .arg(ActivityOption.START_FOR_RESULT)
                .arg(RequestCode.of(1000))
                .go()
        }

        tvServiceBtn.setOnClickListener {
            for (i in 1..5) {
                NRouter.route("/suba/service/aservice").get(IService::class)?.printName()
            }

            for (i in 1..5) {
                NRouter.route("/subb/service/bservice").get(IService::class)?.printName()
            }

            cService?.printName()
        }

        tvPageLog.setOnClickListener {
            NRouter.instance().get(IPageLogger::class)?.printLog()
        }

        NRouter.injector().inject(this)

        NRouter.instance().get(IPageLogger::class)?.logPage("MainActivity")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.i("MainActivity", "onActivityResult requestCode=$requestCode resultCode=$resultCode")
    }
}
