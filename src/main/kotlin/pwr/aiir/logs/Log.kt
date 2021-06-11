package pwr.aiir.logs

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer
import org.bson.types.ObjectId
import pwr.aiir.annotation.NoArg
import java.time.Instant

@NoArg
data class Log (
    @JsonSerialize(using = ToStringSerializer::class)
    var id : ObjectId? = null,
    var created: Instant? = null,
    var thread: String? = null,
    var level: String? = null,
    var loggerName: String? = null,
    var message: String? = null,
    var endOfBatch: Boolean? = null,
    var loggerFqcn: String? = null,
    var threadId: Int? = null,
    var threadPriority: Int? = null,
)



