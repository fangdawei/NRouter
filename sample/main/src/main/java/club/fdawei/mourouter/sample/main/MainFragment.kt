package club.fdawei.mourouter.sample.main

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import club.fdawei.mourouter.annotation.Autowired
import club.fdawei.mourouter.annotation.Route
import club.fdawei.mourouter.api.MouRouter

/**
 * Create by david on 2019/06/22.
 */
@Route(path = "/main/fragment/home")
class MainFragment : Fragment() {

    @Autowired
    var from: String? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        MouRouter.injector().inject(this)
        Log.i("MainFragment", "onActivityCreated from $from")
    }
}