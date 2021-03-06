package pwr.aiir.logs

import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer
import org.bson.types.ObjectId
import java.time.Instant

open class Log (
    @JsonSerialize(using = ToStringSerializer::class)
    var id : ObjectId? = null,
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



