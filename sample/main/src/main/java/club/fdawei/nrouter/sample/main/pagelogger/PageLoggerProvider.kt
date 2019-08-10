package club.fdawei.nrouter.sample.main.pagelogger

import club.fdawei.nrouter.api.action.RouteActionBundle
import club.fdawei.nrouter.api.base.TypeBundle
import club.fdawei.nrouter.api.route.RouteHandler
import club.fdawei.nrouter.api.route.RouteNodeInfo
import club.fdawei.nrouter.api.route.RouteNodeMeta
import club.fdawei.nrouter.sample.base.IPageLogger

/**
 * Create by david on 2019/08/06.
 */
class PageLoggerProvider(
    private val pageLogger: IPageLogger
) : RouteHandler {

    override fun get(data: RouteActionBundle, info: RouteNodeInfo?): Any? {
        return pageLogger
    }

    companion object {
        fun routeNodeMeta(pageLogger: IPageLogger): RouteNodeMeta {
            return RouteNodeMeta(
                "/common/page/logger",
                PageLogger::class,
                0,
                "页面跳转日志",
                TypeBundle.of(PageLoggerProvider::class) {
                    PageLoggerProvider(
                        pageLogger
                    )
                }
            )
        }
    }
}