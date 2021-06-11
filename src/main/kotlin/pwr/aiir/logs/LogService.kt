package pwr.aiir.logs

import io.reactivex.Single
import org.slf4j.LoggerFactory
import java.math.BigInteger
import java.time.Instant
import java.util.logging.Logger
import javax.inject.Singleton


@Singleton
class LogService(private val logMongoRepository: LogMongoRepository) {

    private val logger = logger<LogService>()

    fun saveLog(logSchema : LogSchema) {
        val log : Log = toLog(logSchema)
        logMongoRepository.save(log).subscribe({ value -> logger?.debug("Saved new log with id ${value?.id}") }, { error -> logger?.error("Couldn't save log due to: $error")})
    }

    private fun toLog(logSchema: LogSchema) : Log {
        return Log(created = logSchema.timeMillis?.let { Instant.ofEpochMilli(it) },
                        message = logSchema.message,
                        thread =  logSchema.thread,
                        level = logSchema.level,
                        loggerName = logSchema.loggerName,
                        endOfBatch = logSchema.endOfBatch,
                        loggerFqcn = logSchema.loggerFqcn,
                        threadId = logSchema.threadId,
                        threadPriority = logSchema.threadPriority)
    }
}

inline fun <reified T> logger(): org.slf4j.Logger? {
    return LoggerFactory.getLogger(T::class.java)
}
