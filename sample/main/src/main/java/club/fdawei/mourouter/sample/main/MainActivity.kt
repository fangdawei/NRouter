package club.fdawei.mourouter.sample.main

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import club.fdawei.mourouter.annotation.Autowired
import club.fdawei.mourouter.annotation.Route
import club.fdawei.mourouter.api.MouRouter
import club.fdawei.mourouter.sample.base.service.IService
import kotlinx.android.synthetic.main.activity_main.*

@Route(path = "/main/page/home")
class MainActivity : AppCompatActivity() {

    @Autowired(name = "/subc/service/cservice")
    var cService: IService? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tv_apage_btn.setOnClickListener {
            MouRouter.route("/suba/page/home").env(this).go()
        }

        tv_bpage_btn.setOnClickListener {
            MouRouter.route("/subb/page/home").env(this).go()
        }

        tv_cpage_btn.setOnClickListener {
            MouRouter.route("/subc/page/home").env(this).go()
        }

        tv_service_btn.setOnClickListener {
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
}
