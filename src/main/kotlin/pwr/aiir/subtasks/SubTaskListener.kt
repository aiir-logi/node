package pwr.aiir.subtasks

import io.micronaut.configuration.kafka.annotation.KafkaListener
import io.micronaut.configuration.kafka.annotation.OffsetReset
import io.micronaut.configuration.kafka.annotation.Topic
import io.micronaut.http.annotation.Body
import pwr.aiir.SubTaskResults.SubTaskResult
import pwr.aiir.SubTaskResults.SubTaskResultsRepository
import pwr.aiir.logs.Log
import pwr.aiir.logs.LogMongoRepository

@KafkaListener(offsetReset = OffsetReset.EARLIEST)
class SubTaskListener(private val logMongoRepository: LogMongoRepository,
                      private val subTaskRepository: SubTaskResultsRepository) {

    @Topic("subTask")
    fun receiveSubTask(@Body subTask: SubTask){
        val logs = logMongoRepository.findAllByDateBetween(subTask.startDate, subTask.endDate)?.blockingGet()
        subTaskRepository.save(SubTaskResult(subTask = subTask, results = logs as List<Log>))
    }
}