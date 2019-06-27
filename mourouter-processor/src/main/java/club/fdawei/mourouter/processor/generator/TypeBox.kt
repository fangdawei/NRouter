package club.fdawei.mourouter.processor.generator

import com.squareup.kotlinpoet.ClassName

/**
 * Create by david on 2019/05/27.
 */
object TypeBox {
    const val ACTIVITY_NAME = "android.app.Activity"
    const val SERVICE_NAME = "android.app.Service"
    const val FRAGMENT_NAME = "android.app.Fragment"
    const val V4_FRAGMENT_NAME = "android.support.v4.app.Fragment"
    const val CREATABLE_NAME = "club.fdawei.mourouter.api.component.instance.Creatable"
    const val ROUTE_HANDLER_NAME = "club.fdawei.mourouter.api.route.RouteHandler"
    const val AUTOWIRED_PROVIDER_NAME = "club.fdawei.mourouter.api.inject.AutowiredProvider"

    val ROUTE_TABLE = ClassName.bestGuess(
        "club.fdawei.mourouter.api.route.RouteTable"
    )

    val INJECT_TABLE = ClassName.bestGuess(
        "club.fdawei.mourouter.api.inject.InjectTable"
    )

    val MULTI_PROVIDER = ClassName.bestGuess(
        "club.fdawei.mourouter.api.provider.MultiProvider"
    )

    val ABS_APP_PROVIDER = ClassName.bestGuess(
        "club.fdawei.mourouter.api.provider.AbsAppProvider"
    )

    val HANDLER_META_DATA = ClassName.bestGuess(
        "club.fdawei.mourouter.api.route.HandlerMetaData"
    )

    val INTERCEPTOR_META_DATA = ClassName.bestGuess(
        "club.fdawei.mourouter.api.route.InterceptorMetaData"
    )

    val ACTIVITY_ROUTE_HANDLER = ClassName.bestGuess(
        "club.fdawei.mourouter.api.component.activity.ActivityRouteHandler"
    )

    val FRAGMENT_ROUTE_HANDLER = ClassName.bestGuess(
        "club.fdawei.mourouter.api.component.fragment.FragmentRouteHandler"
    )

    val SERVICE_ROUTE_HANDLER = ClassName.bestGuess(
        "club.fdawei.mourouter.api.component.service.ServiceRouteHandler"
    )

    val INSTANCE_ROUTE_HANDLER = ClassName.bestGuess(
        "club.fdawei.mourouter.api.component.instance.InstanceRouteHandler"
    )

    val INJECTOR = ClassName.bestGuess(
        "club.fdawei.mourouter.api.inject.Injector"
    )

    val AUTOWIRED_PROVIDER = ClassName.bestGuess(AUTOWIRED_PROVIDER_NAME)

    val INJECTOR_META_DATA = ClassName.bestGuess(
        "club.fdawei.mourouter.api.inject.InjectorMetaData"
    )

    val PROVIDER_META_DATA = ClassName.bestGuess(
        "club.fdawei.mourouter.api.inject.ProviderMetaData"
    )

    val ACTION_DATA = ClassName.bestGuess("club.fdawei.mourouter.api.action.ActionData")
}