package pwr.aiir.subtasks

import pwr.aiir.annotation.NoArg
import pwr.aiir.filter.Filter
import pwr.aiir.logs.Log
import pwr.aiir.logs.LogEntity
import java.time.Instant
import java.util.*
import javax.persistence.*

@Entity
@NoArg
data class SubTask (
    @Id
    @GeneratedValue
    var id: UUID?,
    var startDate: Instant,
    var endDate: Instant,
    @OneToMany(cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.EAGER)
    var filters: List<Filter>?,
    @OneToMany(cascade = [CascadeType.ALL])
    var results: List<LogEntity>?
)

