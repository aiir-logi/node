package pwr.aiir.logs

import com.mongodb.client.model.Filters
import io.reactivex.Single
import org.bson.conversions.Bson
import org.slf4j.LoggerFactory
import pwr.aiir.filter.Filter
import pwr.aiir.filter.FilterType
import java.math.BigInteger
import java.time.Instant
import java.util.logging.Logger
import javax.inject.Singleton


@Singleton
class LogService(private val logMongoRepository: LogMongoRepository) {

    private val logger = logger<LogService>()

    fun saveLog(logSchema: LogSchema) {
        val log: Log = toLog(logSchema)
        logMongoRepository.save(log).subscribe({ value -> logger?.debug("Saved new log with id ${value?.id}") }, { error -> logger?.error("Couldn't save log due to: $error") })
    }

    private fun toLog(logSchema: LogSchema): Log {
        return Log(
                created = logSchema.timeMillis?.let { Instant.ofEpochMilli(it) },
                message = logSchema.message,
                thread = logSchema.thread,
                level = logSchema.level,
                loggerName = logSchema.loggerName,
                endOfBatch = logSchema.endOfBatch,
                loggerFqcn = logSchema.loggerFqcn,
                threadId = logSchema.threadId,
                threadPriority = logSchema.threadPriority)
    }

    private fun toLogEntity(log: Log): LogEntity {
        return LogEntity(
                id = log.id.toString(),
                created = log.created,
                message = log.message,
                thread = log.thread,
                level = log.level,
                loggerName = log.loggerName,
                endOfBatch = log.endOfBatch,
                loggerFqcn = log.loggerFqcn,
                threadId = log.threadId,
                threadPriority = log.threadPriority)
    }

    fun findByDatesAndFilters(startDate: Instant, endDate: Instant, filters: List<Filter>?): List<LogEntity>? {
        var queryFilters: MutableList<Bson> = ArrayList()
        queryFilters.add(Filters.and(Filters.gte("created", startDate), Filters.lte("created", endDate)))
        if (filters != null) {
            for (filter in filters) {
                when (filter.type) {
                    FilterType.EQUALS ->
                        queryFilters.add(Filters.eq(filter.fieldName, filter.value))
                    FilterType.NOT_EQUALS ->
                        queryFilters.add(Filters.ne(filter.fieldName, filter.value))
                    FilterType.LESS ->
                        queryFilters.add(Filters.lt(filter.fieldName, filter.value))
                    FilterType.LESS_EQUALS ->
                        queryFilters.add(Filters.lte(filter.fieldName, filter.value))
                    FilterType.GREATER ->
                        queryFilters.add(Filters.gt(filter.fieldName, filter.value))
                    FilterType.GREATER_EQUALS ->
                        queryFilters.add(Filters.gte(filter.fieldName, filter.value))
                    FilterType.REGEX ->
                        queryFilters.add(Filters.regex(filter.fieldName, filter.value))
                }
            }
        }

        var results: List<Log> = logMongoRepository.findByFilter(Filters.and(queryFilters))?.blockingGet() as List<Log>
        return results.map { log -> toLogEntity(log) }
    }
}

inline fun <reified T> logger(): org.slf4j.Logger? {
    return LoggerFactory.getLogger(T::class.java)
}
