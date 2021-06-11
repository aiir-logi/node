package pwr.aiir.subtasks

import io.micronaut.data.annotation.Repository
import io.micronaut.data.repository.CrudRepository
import java.util.*

@Repository
interface SubtaskRepository : CrudRepository<SubTask, UUID> {
}
