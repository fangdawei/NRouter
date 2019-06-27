package club.fdawei.mourouter.api.component.instance


/**
 * Created by david on 2019/05/30.
 */
interface Instantiable {

    /**
     * 初始化方法，可能会被调用多次
     */
    fun init(initContext: InitContext) {}
}