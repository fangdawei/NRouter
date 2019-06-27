package club.fdawei.mourouter.sample.main

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import club.fdawei.mourouter.annotation.Autowired
import club.fdawei.mourouter.annotation.Route
import club.fdawei.mourouter.api.MouRouter
import club.fdawei.mourouter.api.component.activity.ActivityOption
import club.fdawei.mourouter.api.component.activity.RequestCode
import club.fdawei.mourouter.sample.base.service.IService
import kotlinx.android.synthetic.main.activity_main.*

@Route(path = "/main/page/home")
class MainActivity : AppCompatActivity() {

    @Autowired(name = "/subc/service/cservice")
    var cService: IService? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvAPageBtn.setOnClickListener {
            MouRouter.route("/suba/page/home").env(this).go()
        }

        tvBPageBtn.setOnClickListener {
            MouRouter.route("/subb/page/home").env(this).go()
        }

        tvCPageBtn.setOnClickListener {
            MouRouter.route("/subc/page/home")
                .env(this)
                .env(ActivityOption.START_FOR_RESULT)
                .env(RequestCode(1000))
                .go()
        }

        tvServiceBtn.setOnClickListener {
            for (i in 1..5) {
                MouRouter.route("/suba/service/aservice").get(IService::class)?.printName()
            }

            for (i in 1..5) {
                MouRouter.route("/subb/service/bservice").get(IService::class)?.printName()
            }

            cService?.printName()
        }

        MouRouter.injector().inject(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.i("MainActivity", "onActivityResult requestCode=$requestCode resultCode=$resultCode")
    }
}
