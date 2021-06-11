package pwr.aiir.subtasks

import io.micronaut.context.annotation.Parameter
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Post
import java.util.*

@Controller("/subtasks")
class SubTaskController(private val subtaskRepository: SubtaskRepository, private val subTaskService: SubTaskService) {

    @Post
    fun save(@Body subtask: SubTask) : HttpResponse<SubTask> {
        return HttpResponse.created(subtaskRepository.save(subtask))
    }

    @Post("/{id}")
    fun run(@Parameter @PathVariable("id") id : UUID) {
        subTaskService.runSubTask(id)
    }
}
