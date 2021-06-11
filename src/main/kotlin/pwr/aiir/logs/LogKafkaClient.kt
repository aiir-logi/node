package pwr.aiir.logs

import io.micronaut.configuration.kafka.annotation.KafkaListener
import io.micronaut.configuration.kafka.annotation.OffsetReset
import io.micronaut.configuration.kafka.annotation.Topic

@KafkaListener(offsetReset = OffsetReset.EARLIEST)
class LogKafkaClient(private val logService: LogService) {

    @Topic("logs")
    fun receive(logSchema: LogSchema) {
        logService.saveLog(logSchema);
    }
}
