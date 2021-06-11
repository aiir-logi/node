package pwr.aiir.logs

import io.micronaut.context.ApplicationContext
import io.micronaut.context.annotation.PropertySource
import io.micronaut.http.HttpHeaders
import io.micronaut.http.MediaType
import io.micronaut.runtime.server.EmbeddedServer
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import io.micronaut.test.support.TestPropertyProvider
import io.restassured.RestAssured
import io.restassured.RestAssured.given
import org.apache.http.HttpStatus
import org.bson.types.ObjectId
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.equalTo
import org.junit.jupiter.api.*
import org.testcontainers.containers.MongoDBContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.utility.DockerImageName
import java.time.Instant
import javax.inject.Inject

@Testcontainers
@MicronautTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class LogControllerTests : TestPropertyProvider {

    private val mongoDBContainer = MongoDBContainer(DockerImageName.parse("mongo:4.0.10")).withExposedPorts(27017).apply { start() }
    private val log : Log = Log(created = Instant.ofEpochMilli(1623421107277), thread = "main",
        level = "ERROR",
        loggerName = "com.example.loggenerator.LoggeneratorApplication",
        message = "Logger works",
        endOfBatch = false,
        loggerFqcn = "org.apache.logging.log4j.spi.AbstractLogger",
        threadId = 1,
        threadPriority = 5)


    @Inject
    private lateinit var embeddedServer : EmbeddedServer

    override fun getProperties(): Map<String, String> {
        return mapOf(
            "MONGO_HOST" to mongoDBContainer.containerIpAddress,
            "MONGO_PORT" to "${mongoDBContainer.getMappedPort(27017)}")
    }

    @BeforeEach
    fun before() {
        RestAssured.port = embeddedServer.port
    }


    @Test
    @Order(0)
    fun list() {
        given()
            .`when`().get("/logs")
            .then()
            .statusCode(HttpStatus.SC_OK)
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
            .body("", `is`(emptyList<Log>()))
    }

    @Test
    @Order(1)
    fun add() {
        given()
            .contentType(MediaType.APPLICATION_JSON).body(log)
            .`when`().post("/logs")
            .then()
            .statusCode(HttpStatus.SC_CREATED)
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
            .body("id", equalTo(log.id.toString()))
    }

    @Test
    @Order(2)
    fun getOne() {
        given()
            .`when`().get("/logs/${log.id.toString()}")
            .then()
            .statusCode(HttpStatus.SC_OK)
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
            .body("id", equalTo(log.id.toString()))
            .body("message", equalTo("Logger works"))
    }


}
