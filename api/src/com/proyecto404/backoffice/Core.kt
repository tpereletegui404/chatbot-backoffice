package com.proyecto404.backoffice

import com.nbottarini.asimov.cqbus.CQBus
import com.nbottarini.asimov.cqbus.ExecutionContext
import com.nbottarini.asimov.cqbus.Middleware
import com.nbottarini.asimov.cqbus.requests.Request
import com.proyecto404.backoffice.base.data.jdbc.DataSource
import com.proyecto404.backoffice.base.data.jooq.JooqConfiguration
import com.proyecto404.backoffice.base.infrastructure.cqbus.LoggingMiddleware
import com.proyecto404.backoffice.base.infrastructure.middlewares.TransactionalMiddleware
import com.proyecto404.backoffice.base.infrastructure.persistence.DatabaseInitializer
import com.proyecto404.backoffice.base.infrastructure.persistence.jooq.JooqRepositoryProvider
import com.proyecto404.backoffice.base.integration.application.CQDispatcher
import com.proyecto404.backoffice.base.integration.eventBus.Event
import com.proyecto404.backoffice.base.integration.eventBus.EventBus
import com.proyecto404.backoffice.core.app.AddContext
import com.proyecto404.backoffice.core.app.GetConfiguration
import com.proyecto404.backoffice.core.security.app.CreateUser
import com.proyecto404.backoffice.core.security.app.Login
import com.proyecto404.backoffice.core.security.domain.sessionToken.UUIDSessionTokenFactory
import com.proyecto404.backoffice.core.security.infrastructure.middlewares.AuthenticationMiddleware
import com.proyecto404.backoffice.core.security.infrastructure.middlewares.AuthorizationMiddleware
import com.proyecto404.backoffice.core.security.infrastructure.passwords.RandomSaltGenerator
import com.proyecto404.backoffice.core.security.infrastructure.passwords.SHA512PasswordEncryptor

class Core(val config: Config): CQDispatcher {
    val services = Services(config)
    private val saltGenerator = RandomSaltGenerator()
    private val passwordEncryptor = SHA512PasswordEncryptor()
    private val sessionTokenFactory = UUIDSessionTokenFactory()

    init {
        config.databaseInitializer.run()
        initializeMiddlewares()
        registerHandlers()
    }

    private fun registerHandlers() = with(config.cqBus) {
        registerHandler { CreateUser.Handler(services.repositories, saltGenerator, passwordEncryptor) }
        registerHandler { Login.Handler(services.repositories, passwordEncryptor, sessionTokenFactory) }
        registerHandler { AddContext.Handler(services.repositories) }
        registerHandler { GetConfiguration.Handler(services.repositories) }
    }

    private fun initializeMiddlewares() = with(config.cqBus) {
        config.middlewares.forEach { registerMiddleware(it) }
        registerMiddleware(TransactionalMiddleware(config.dataSource.transactionManager))
        registerMiddleware(LoggingMiddleware())
        registerMiddleware(AuthorizationMiddleware())
        registerMiddleware(AuthenticationMiddleware(services.repositories.get()))
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
