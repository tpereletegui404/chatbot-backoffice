package com.proyecto404.backoffice.modules.common

import com.nbottarini.asimov.cqbus.CQBus
import com.nbottarini.asimov.cqbus.ExecutionContext
import com.nbottarini.asimov.cqbus.Middleware
import com.nbottarini.asimov.cqbus.requests.Request
import com.proyecto404.backoffice.modules.accounting.AccountingModule
import com.proyecto404.backoffice.modules.common.base.data.jdbc.DataSource
import com.proyecto404.backoffice.modules.common.base.data.jooq.JooqConfiguration
import com.proyecto404.backoffice.modules.common.base.integration.application.CQDispatcher
import com.proyecto404.backoffice.modules.common.base.integration.eventBus.Event
import com.proyecto404.backoffice.modules.common.base.integration.eventBus.EventBus
import com.proyecto404.backoffice.modules.common.infrastructure.cqbus.LoggingMiddleware
import com.proyecto404.backoffice.modules.common.infrastructure.middlewares.TransactionalMiddleware
import com.proyecto404.backoffice.modules.common.infrastructure.persistence.DatabaseInitializer
import com.proyecto404.backoffice.modules.common.infrastructure.persistence.jooq.JooqRepositoryProvider
import com.proyecto404.backoffice.modules.security.SecurityModule

class Core(val config: Config): CQDispatcher {
    val services = Services(config)

    init {
        config.databaseInitializer.run()
        initializeModules(config, services)
        initializeMiddlewares()
    }

    private fun initializeModules(config: Config, services: Services) {
        AccountingModule(config, services)
        SecurityModule(config, services)
    }

    private fun initializeMiddlewares() = with(config.cqBus) {
        config.middlewares.forEach { registerMiddleware(it) }
        registerMiddleware(TransactionalMiddleware(config.dataSource.transactionManager))
        registerMiddleware(LoggingMiddleware())
    }

    override fun <T: Request<R>, R> execute(request: T, context: ExecutionContext) = config.cqBus.execute(request, context)

    override fun notify(event: Event) { services.eventBus.publish(event) }

    fun registerMiddleware(middleware: Middleware) {
        config.cqBus.registerMiddleware(middleware)
    }

    class Services(val config: Config) {
        val jooq = JooqConfiguration(config.dataSource, config.logSql)
        val repositories = JooqRepositoryProvider(jooq, config.eventBus)
        val cqBus = config.cqBus
        val eventBus = config.eventBus
    }

    data class Config(
        val cqBus: CQBus,
        val eventBus: EventBus,
        val dataSource: DataSource,
        val databaseInitializer: DatabaseInitializer,
        val logSql: Boolean = false,
        val middlewares: List<Middleware> = emptyList()
    )
}
