package club.fdawei.nrouter.api.component.creatable


/**
 * Created by david on 2019/05/30.
 */
interface Creatable {

    /**
     * 初始化方法，可能会被调用多次
     */
    fun init(context: Context) {}
}