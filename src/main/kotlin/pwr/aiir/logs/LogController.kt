package pwr.aiir.logs

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.*
import javax.validation.Valid
import org.bson.types.ObjectId
import pwr.aiir.logs.Log
import pwr.aiir.logs.LogMongoRepository


@Controller("/logs")
class LogController(private val logRepository : LogMongoRepository) {


    @Get("/{id}")
    fun show(id: String?): Log? {
        return logRepository.findById(ObjectId(id))?.blockingGet()
    }

    @Get
    fun findAll(): List<Log?>? {
        return logRepository.findAll()?.blockingGet()
    }

    @Post
    fun save(@Body log: @Valid Log): HttpResponse<Log?>? {
        return HttpResponse.created(logRepository.save(log).blockingGet())
    }
}
