package pwr.aiir.subtasks

import io.micronaut.configuration.kafka.annotation.KafkaListener
import io.micronaut.configuration.kafka.annotation.OffsetReset
import io.micronaut.configuration.kafka.annotation.Topic
import io.micronaut.http.annotation.Body

@KafkaListener(offsetReset = OffsetReset.EARLIEST)
class SubTaskKafkaClient(private val subTaskService: SubTaskService) {

    @Topic("subTask")
    fun receiveSubTask(@Body subTask: SubTask) {
        subTaskService.runSubTask(subTask.id!!);
    }
}
