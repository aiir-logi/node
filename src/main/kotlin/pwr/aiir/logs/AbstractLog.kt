package pwr.aiir.logs

import pwr.aiir.annotation.NoArg

@NoArg
abstract class AbstractLog {
    abstract var thread: String?
    abstract var level: String?
    abstract var loggerName: String?
    abstract var message: String?
    abstract var endOfBatch: Boolean?
    abstract var loggerFqcn: String?
    abstract var threadId: Int?
    abstract var threadPriority: Int?
}
