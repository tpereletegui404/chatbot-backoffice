package com.proyecto404.backoffice.core.common.infrastructure.persistance.jooq

import com.nbottarini.asimov.time.Clock
import com.proyecto404.backoffice.base.data.jdbc.dataSource
import com.proyecto404.backoffice.base.data.jooq.JooqConfiguration
import com.proyecto404.backoffice.base.integration.eventBus.FakeEventBus
import com.proyecto404.backoffice.base.transactions.Transaction
import com.proyecto404.backoffice.core.common.domain.DateExamples.now
import com.proyecto404.backoffice.core.common.domain.ScenarioBuilder
import com.proyecto404.backoffice.base.infrastructure.persistence.jooq.JooqRepositoryProvider
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Tag

@Tag("slow")
abstract class BaseJooqTest {
    private val dataSource =
        com.proyecto404.backoffice.base.data.jdbc.dataSource { dbCredentialsFromEnv("TEST_DB"); simpleDbConnections(); simpleTransactions() }
    protected val eventBus = FakeEventBus()
    protected val jooq = JooqConfiguration(dataSource)
    protected val repositories = JooqRepositoryProvider(jooq, eventBus)
    protected val scenario = ScenarioBuilder(repositories)
    private lateinit var currentTransaction: Transaction

    @BeforeEach
    fun baseSetup() {
        Clock.stoppedAt(now)
        currentTransaction = dataSource.transactionManager.beginTransaction()
    }


    @AfterEach
    fun baseTearDown() {
        currentTransaction.rollback()
    }
}
