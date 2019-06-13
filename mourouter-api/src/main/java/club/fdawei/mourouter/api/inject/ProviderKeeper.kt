package club.fdawei.mourouter.api.inject

/**
 * Created by david on 2019/06/05.
 */
class ProviderKeeper(
    private val creator: () -> AutowiredProvider
) {
    val provider: AutowiredProvider by lazy { creator.invoke() }
}