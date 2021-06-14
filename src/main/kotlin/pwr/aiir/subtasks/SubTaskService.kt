package pwr.aiir.subtasks

import pwr.aiir.logs.LogService
import java.util.*
import javax.inject.Singleton

@Singleton
class SubTaskService(private val logService: LogService, private val subtaskRepository: SubtaskRepository) {

    fun runSubTask(id: UUID) {
        var subtaskOptional = subtaskRepository.findById(id)
        if (subtaskOptional.isPresent) {
            var subtask = subtaskOptional.get()

            subtask.subTaskStatus = SubTaskStatus.IN_PROGRESS
            subtaskRepository.update(subtask)

            subtask.results = logService.findByDatesAndFilters(subtask.startDate, subtask.endDate, subtask.filters)
            subtask.subTaskStatus = SubTaskStatus.DONE

            subtaskRepository.update(subtask)
        }
    }
}
