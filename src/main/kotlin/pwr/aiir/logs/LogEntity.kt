package pwr.aiir.logs

import java.time.Instant
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class LogEntity (
    @Id
    var id : String?,
    var created: Instant?,
    override var thread: String?,
    override var level: String?,
    override var loggerName: String?,
    override var message: String?,
    override var endOfBatch: Boolean?,
    override var loggerFqcn: String?,
    override var threadId: Int?,
    override var threadPriority: Int?
) : AbstractLog()
