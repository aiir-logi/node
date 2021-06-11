package pwr.aiir.logs

import java.time.Instant


class LogSchema {
    var timeMillis: Long? = null
    var thread: String? = null
    var level: String? = null
    var loggerName: String? = null
    var message: String? = null
    var endOfBatch: Boolean? = null
    var loggerFqcn: String? = null
    var threadId: Int? = null
    var threadPriority: Int? = null
}
