package club.fdawei.nrouter.processor.log

import club.fdawei.nrouter.processor.common.LOG_TAG
import javax.annotation.processing.Messager
import javax.tools.Diagnostic

/**
 * Created by david on 2019/05/27.
 */
class Logger {

    var messager:Messager? = null

    fun i(msg: String) {
        messager?.printMessage(Diagnostic.Kind.NOTE, "$LOG_TAG $msg")
    }

    fun w(msg: String) {
        messager?.printMessage(Diagnostic.Kind.WARNING, "$LOG_TAG $msg")
    }

    fun e(msg: String) {
        messager?.printMessage(Diagnostic.Kind.ERROR, "$LOG_TAG $msg")
    }
}