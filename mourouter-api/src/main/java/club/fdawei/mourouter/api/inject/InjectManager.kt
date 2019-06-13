package club.fdawei.mourouter.api.inject

import android.app.Activity
import club.fdawei.mourouter.api.MouRouter
import club.fdawei.mourouter.api.common.COMMON_TAG
import club.fdawei.mourouter.api.component.activity.ActivityAutowiredProvider
import club.fdawei.mourouter.api.component.fragment.FragmentAutowiredProvider
import club.fdawei.mourouter.api.provider.MultiProvider

/**
 * Created by david on 2019/06/05.
 */
class InjectManager {

    private val activityProvider: AutowiredProvider by lazy { ActivityAutowiredProvider() }
    private val fragmentProvider: AutowiredProvider by lazy { FragmentAutowiredProvider() }
    private val defaultProvider: AutowiredProvider by lazy { AutowiredProvider() }

    private val injectTable = InjectTable()

    fun loadInjectInfo(provider: MultiProvider?) {
        provider?.provide(injectTable)
        MouRouter.logger.d(COMMON_TAG, "load inject table finish ${injectTable.print()}")
    }

    fun getInjector(target: Any): Injector? {
        return injectTable.getInjector(target::class)
    }

    fun getProvider(source: Any): AutowiredProvider? {
        return injectTable.getProvider(source::class) ?: when (source) {
            is Activity -> {
                activityProvider
            }
            is android.app.Fragment,
            is android.support.v4.app.Fragment -> {
                fragmentProvider
            }
            else -> {
                defaultProvider
            }
        }
    }
}