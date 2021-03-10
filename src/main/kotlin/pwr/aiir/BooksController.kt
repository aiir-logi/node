package pwr.aiir

import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import org.slf4j.LoggerFactory

@Controller("/books")
class BooksController {

    val log = LoggerFactory.getLogger(javaClass)

    @Get
    internal fun index(): List<Book> {
        log.info("Hello from node 1")
        return listOf(Book("1491950358", "Building Microservices"),
            Book("1680502395", "Release It!"),
            Book("0321601912", "Continuous Delivery:"))
    }
}
