package club.fdawei.mourouter.sample.subc

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import club.fdawei.mourouter.annotation.Route
import club.fdawei.mourouter.api.MouRouter
import kotlinx.android.synthetic.main.activity_c.*

@Route(path = "/subc/page/home")
class CActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_c)

        tvMainPageBtn.setOnClickListener {
            MouRouter.route("/main/page/home").go()
        }

        tvAPageBtn.setOnClickListener {
            MouRouter.route("/suba/page/home").go()
        }

        tvBPageBtn.setOnClickListener {
            MouRouter.route("/subb/page/home").go()
        }

        tvFinishBtn.setOnClickListener {
            setResult(10000)
            finish()
        }

        val mainFragment = MouRouter.route("/main/fragment/home")
            .withString("from", "CActivity")
            .get(Fragment::class)
        if (mainFragment != null) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.flContainer, mainFragment)
                .commit()
        }

    }
}
