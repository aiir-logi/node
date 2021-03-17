package pwr.aiir.logs

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer
import org.bson.types.ObjectId
import pwr.aiir.annotation.NoArg
import java.time.Instant

@NoArg
data class Log(
    @JsonSerialize(using = ToStringSerializer::class)
    var id : ObjectId?,
    var created : Instant,
    var details : Map<String, Any>
)
