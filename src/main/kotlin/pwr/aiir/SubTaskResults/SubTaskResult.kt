package pwr.aiir.SubTaskResults

import io.micronaut.data.annotation.GeneratedValue
import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.MappedEntity
import pwr.aiir.logs.Log
import pwr.aiir.subtasks.SubTask
import javax.persistence.GenerationType

@MappedEntity
class SubTaskResult(
        @Id
        @GeneratedValue(value = GeneratedValue.Type.AUTO)
        var id: String? = null,
        var subTask: SubTask,
        var results: List<Log>
)