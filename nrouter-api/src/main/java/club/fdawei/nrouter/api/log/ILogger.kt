package club.fdawei.nrouter.api.log

/**
 * Create by david on 2019/05/26.
 */
interface ILogger {
    fun i(tag: String, msg: String)

    fun d(tag: String, msg: String)

    fun e(tag: String, msg: String)
}