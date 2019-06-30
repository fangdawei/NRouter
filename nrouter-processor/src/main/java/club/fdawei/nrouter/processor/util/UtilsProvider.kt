package club.fdawei.nrouter.processor.util

import club.fdawei.nrouter.processor.log.Logger
import javax.lang.model.util.Elements
import javax.lang.model.util.Types

/**
 * Create by david on 2019/05/27.
 */
class UtilsProvider {
    lateinit var logger: Logger
    lateinit var elementUtils: Elements
    lateinit var typeUtils: Types
}