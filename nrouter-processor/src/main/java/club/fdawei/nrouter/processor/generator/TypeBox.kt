package club.fdawei.nrouter.processor.generator

import com.squareup.kotlinpoet.ClassName

/**
 * Create by david on 2019/05/27.
 */
object TypeBox {
    const val ACTIVITY_NAME = "android.app.Activity"
    const val SERVICE_NAME = "android.app.Service"
    const val FRAGMENT_NAME = "android.app.Fragment"
    const val V4_FRAGMENT_NAME = "android.support.v4.app.Fragment"
    const val CREATABLE_NAME = "club.fdawei.nrouter.api.component.creatable.Creatable"
    const val ROUTE_HANDLER_NAME = "club.fdawei.nrouter.api.route.RouteHandler"
    const val AUTOWIRED_PROVIDER_NAME = "club.fdawei.nrouter.api.inject.AutowiredProvider"

    val ROUTE_TABLE = ClassName.bestGuess(
        "club.fdawei.nrouter.api.route.RouteTable"
    )

    val INJECT_TABLE = ClassName.bestGuess(
        "club.fdawei.nrouter.api.inject.InjectTable"
    )

    val MULTI_PROVIDER = ClassName.bestGuess(
        "club.fdawei.nrouter.api.provider.MultiProvider"
    )

    val ABS_APP_PROVIDER = ClassName.bestGuess(
        "club.fdawei.nrouter.api.provider.AbsAppProvider"
    )

    val ROUTE_NODE_META_DATA = ClassName.bestGuess(
        "club.fdawei.nrouter.api.route.RouteNodeMetaData"
    )

    val INTERCEPTOR_META_DATA = ClassName.bestGuess(
        "club.fdawei.nrouter.api.route.InterceptorMetaData"
    )

    val ACTIVITY_ROUTE_HANDLER = ClassName.bestGuess(
        "club.fdawei.nrouter.api.component.activity.ActivityRouteHandler"
    )

    val FRAGMENT_ROUTE_HANDLER = ClassName.bestGuess(
        "club.fdawei.nrouter.api.component.fragment.FragmentRouteHandler"
    )

    val SERVICE_ROUTE_HANDLER = ClassName.bestGuess(
        "club.fdawei.nrouter.api.component.service.ServiceRouteHandler"
    )

    val CREATABLE_ROUTE_HANDLER = ClassName.bestGuess(
        "club.fdawei.nrouter.api.component.creatable.CreatableRouteHandler"
    )

    val INJECTOR = ClassName.bestGuess(
        "club.fdawei.nrouter.api.inject.Injector"
    )

    val AUTOWIRED_PROVIDER = ClassName.bestGuess(AUTOWIRED_PROVIDER_NAME)

    val INJECTOR_META_DATA = ClassName.bestGuess(
        "club.fdawei.nrouter.api.inject.InjectorMetaData"
    )

    val PROVIDER_META_DATA = ClassName.bestGuess(
        "club.fdawei.nrouter.api.inject.ProviderMetaData"
    )

    val ACTION_BUNDLE = ClassName.bestGuess("club.fdawei.nrouter.api.action.ActionBundle")
}