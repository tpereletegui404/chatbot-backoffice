package com.proyecto404.backoffice.e2e.testing

import com.nbottarini.asimov.cqbus.CQBus
import com.nbottarini.asimov.time.Clock
import com.proyecto404.backoffice.http.HttpApp
import com.proyecto404.backoffice.modules.common.Core
import com.proyecto404.backoffice.modules.common.base.data.jdbc.dataSource
import com.proyecto404.backoffice.modules.common.base.http.server.HttpServer
import com.proyecto404.backoffice.modules.common.base.integration.eventBus.InProcessEventBus
import com.proyecto404.backoffice.modules.common.base.transactions.Transaction
import com.proyecto404.backoffice.modules.common.domain.DateExamples.now
import com.proyecto404.backoffice.modules.common.domain.ScenarioBuilder
import com.proyecto404.backoffice.modules.common.infrastructure.persistence.DatabaseInitializer
import com.proyecto404.backoffice.modules.security.app.authorization.Roles
import com.proyecto404.backoffice.modules.security.domain.user
import com.proyecto404.backoffice.modules.security.infrastructure.passwords.SHA512PasswordEncryptor
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Tag

@Tag("slow")
abstract class BaseApiTest {
    private val port = 6262
    private val baseUrl = "http://localhost:$port"
    private lateinit var currentTransaction: Transaction
    private lateinit var app: HttpApp
    protected val core = Core(createCoreConfig())
    protected val passwordEncryptor = SHA512PasswordEncryptor()
    protected val scenario by lazy { ScenarioBuilder(repositories, passwordEncryptor) }
    protected val repositories by lazy { core.services.repositories }
    protected val currentUser by lazy { scenario.user { authenticated() } }

    fun RequestBuilder.asUser() = asUser(currentUser)

    fun RequestBuilder.asAccountant() = asUser(scenario.user { username("accountant"); authenticated(); roles(Roles.Administrative) })

    fun RequestBuilder.asAdmin() = asUser(scenario.user { username("admin"); authenticated(); roles(Roles.Admin) })

    fun request() = RequestBuilder(baseUrl)

    private fun createCoreConfig(): Core.Config {
        val dataSource = dataSource {
            dbCredentialsFromEnv("TEST_DB")
            simpleDbConnections()
            simpleTransactions()
        }
        return Core.Config(
            CQBus(),
            InProcessEventBus(),
            dataSource,
            databaseInitializer = DatabaseInitializer(dataSource.credentials(), runMigrations = false)
        )
    }

    @BeforeEach
    fun baseSetup() {
        Clock.stoppedAt(now)
        app = HttpApp(HttpApp.Config(HttpServer(port), core))
        currentTransaction = core.config.dataSource.transactionManager.beginTransaction()
        app.start()
    }


    @AfterEach
    fun baseTearDown() {
        app.stop()
        currentTransaction.rollback()
    }
}
