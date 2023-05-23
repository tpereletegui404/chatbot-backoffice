package com.proyecto404.backoffice.e2e

import com.google.gson.JsonParseException
import com.nbottarini.asimov.cqbus.CQBus
import com.nbottarini.asimov.cqbus.requests.Request
import com.proyecto404.backoffice.e2e.testing.RequestBuilder
import com.proyecto404.backoffice.http.HttpApp
import com.proyecto404.backoffice.Core
import com.proyecto404.backoffice.base.data.jdbc.dataSource
import com.proyecto404.backoffice.base.domain.errors.DomainError
import com.proyecto404.backoffice.base.domain.errors.NotFoundError
import com.proyecto404.backoffice.base.http.server.HttpServer
import com.proyecto404.backoffice.base.http.server.controllers.errors.ParameterError
import com.proyecto404.backoffice.base.integration.eventBus.InProcessEventBus
import com.proyecto404.backoffice.base.transactions.Transaction
import com.proyecto404.backoffice.base.infrastructure.persistence.DatabaseInitializer
import com.proyecto404.backoffice.base.auth.NotAuthenticatedError
import com.proyecto404.backoffice.base.auth.UnauthorizedAccessError
import io.mockk.every
import io.mockk.mockk
import org.hamcrest.CoreMatchers.equalTo
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test

@Disabled
@Tag("slow")
class ErrorMappingsTest {
    @Test
    fun `not authenticated`() {
        every { cqBus.execute(any<Request<*>>(), any()) } throws com.proyecto404.backoffice.base.auth.NotAuthenticatedError()

        val response = execSomeCommand()

        response.assertThat().statusCode(401)
        response.assertThat().body("type", equalTo("NotAuthenticatedError"))
        response.assertThat().body("message", equalTo("Not Authenticated"))
    }

    @Test
    fun `unauthorized access`() {
        every { cqBus.execute(any<Request<*>>(), any()) } throws com.proyecto404.backoffice.base.auth.UnauthorizedAccessError()

        val response = execSomeCommand()

        response.assertThat().statusCode(403)
        response.assertThat().body("type", equalTo("UnauthorizedAccessError"))
        response.assertThat().body("message", equalTo("Unauthorized access"))
    }

    @Test
    fun `not found`() {
        every { cqBus.execute(any<Request<*>>(), any()) } throws NotFoundError()

        val response = execSomeCommand()

        response.assertThat().statusCode(404)
        response.assertThat().body("type", equalTo("NotFoundError"))
        response.assertThat().body("message", equalTo("Not found"))
    }

    @Test
    fun `domain error`() {
        every { cqBus.execute(any<Request<*>>(), any()) } throws SomeDomainError("Some message")

        val response = execSomeCommand()

        response.assertThat().statusCode(400)
        response.assertThat().body("type", equalTo("SomeDomainError"))
        response.assertThat().body("message", equalTo("Some message"))
    }

    @Test
    fun `json parse exception`() {
        every { cqBus.execute(any<Request<*>>(), any()) } throws JsonParseException("Some message")

        val response = execSomeCommand()

        response.assertThat().statusCode(400)
        response.assertThat().body("type", equalTo("JsonParseException"))
        response.assertThat().body("message", equalTo("Some message"))
    }

    @Test
    fun `parameter error`() {
        every { cqBus.execute(any<Request<*>>(), any()) } throws ParameterError("Some message")

        val response = execSomeCommand()

        response.assertThat().statusCode(400)
        response.assertThat().body("type", equalTo("ParameterError"))
        response.assertThat().body("message", equalTo("Some message"))
    }

    @Test
    fun `internal error`() {
        every { cqBus.execute(any<Request<*>>(), any()) } throws Exception("Some message")

        val response = execSomeCommand()

        response.assertThat().statusCode(500)
        response.assertThat().body("type", equalTo(null))
        response.assertThat().body("message", equalTo("Internal error"))
    }

    @BeforeEach
    fun setup() {
        app = HttpApp(HttpApp.Config(HttpServer(port), core))
        currentTransaction = core.config.dataSource.transactionManager.beginTransaction()
        app.start()
    }

    @AfterEach
    fun tearDown() {
        app.stop()
        currentTransaction.rollback()
    }

    private fun createCoreConfig(): Core.Config {
        val dataSource = com.proyecto404.backoffice.base.data.jdbc.dataSource {
            dbCredentialsFromEnv("TEST_DB")
            simpleDbConnections()
            simpleTransactions()
        }
        return Core.Config(
            cqBus,
            InProcessEventBus(),
            dataSource,
            DatabaseInitializer(dataSource.credentials(), runMigrations = false)
        )
    }

    private fun execSomeCommand() = RequestBuilder(baseUrl).delete("/accounting/transactions/1").exec()

    private val port = 6061
    private val baseUrl = "http://localhost:$port"
    private val cqBus = mockk<CQBus>(relaxed = true)
    private val core = Core(createCoreConfig())
    private lateinit var app: HttpApp
    private lateinit var currentTransaction: Transaction

    class SomeDomainError(message: String): DomainError(message)
}
