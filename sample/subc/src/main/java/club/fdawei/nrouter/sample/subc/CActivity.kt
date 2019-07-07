package club.fdawei.nrouter.sample.subc

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import club.fdawei.nrouter.annotation.Autowired
import club.fdawei.nrouter.annotation.Route
import club.fdawei.nrouter.api.NRouter
import club.fdawei.nrouter.sample.base.IPageLogger
import kotlinx.android.synthetic.main.activity_c.*

@Route(path = "/subc/page/home", desc = "C页面")
class CActivity : AppCompatActivity() {

    @Autowired
    var from: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_c)

        NRouter.injector().inject(this)

        tvFrom.text = String.format("from %s", from ?: "unknown")

        tvMainPageBtn.setOnClickListener {
            NRouter.route("/main/page/home").go()
        }

        tvAPageBtn.setOnClickListener {
            NRouter.route("/suba/page/home").go()
        }

        tvBPageBtn.setOnClickListener {
            NRouter.route("/subb/page/home").go()
        }

        tvFinishBtn.setOnClickListener {
            setResult(10000)
            finish()
        }

        val mainFragment = NRouter.route("/main/fragment/home")
            .withString("from", "CActivity")
            .get(Fragment::class)
        if (mainFragment != null) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.flContainer, mainFragment)
                .commit()
        }

        NRouter.instance().get(IPageLogger::class)?.logPage("CActivity")
    }
}
