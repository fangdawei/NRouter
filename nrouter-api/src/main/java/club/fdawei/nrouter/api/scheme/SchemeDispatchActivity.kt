package club.fdawei.nrouter.api.scheme

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import club.fdawei.nrouter.api.NRouter
import club.fdawei.nrouter.api.common.COMMON_TAG

/**
 * Create by david on 2019/07/02.
 */
class SchemeDispatchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val uri = intent.data
        NRouter.logger.i(COMMON_TAG, "dispatch scheme=$uri")
        NRouter.handleScheme(intent)
        finish()
    }
}