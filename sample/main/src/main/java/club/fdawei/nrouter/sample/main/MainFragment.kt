package club.fdawei.nrouter.sample.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import club.fdawei.nrouter.annotation.Autowired
import club.fdawei.nrouter.annotation.Route
import club.fdawei.nrouter.api.NRouter
import kotlinx.android.synthetic.main.fragment_main.*

/**
 * Create by david on 2019/06/22.
 */
@Route(path = "/main/fragment/home")
class MainFragment : Fragment() {

    @Autowired
    var from: String? = null

    @Autowired
    var name: String? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        NRouter.injector().inject(this)
        tvName.text = "Here is $name"
        Log.i("MainFragment", "onActivityCreated from $from")
    }
}