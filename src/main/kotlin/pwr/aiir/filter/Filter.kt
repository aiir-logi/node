package pwr.aiir.filter

import pwr.aiir.annotation.NoArg
import java.util.*
import javax.persistence.Embeddable
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
@NoArg
data class Filter (
    @Id
    @GeneratedValue
    var id: UUID?,
    var fieldName: String,
    var type: FilterType,
    var value: String
)
