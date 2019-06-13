package club.fdawei.mourouter.api.inject

/**
 * Created by david on 2019/06/05.
 */
class InjectorKeeper(
    private val creator: () -> Injector
) {
    val injector: Injector by lazy { creator.invoke() }
}