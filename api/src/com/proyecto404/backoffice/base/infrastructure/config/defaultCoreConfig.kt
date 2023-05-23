package com.proyecto404.backoffice.base.infrastructure.config

import com.nbottarini.asimov.cqbus.CQBus
import com.nbottarini.asimov.environment.Env
import com.proyecto404.backoffice.Core
import com.proyecto404.backoffice.base.data.jdbc.dataSource
import com.proyecto404.backoffice.base.integration.eventBus.InProcessEventBus
import com.proyecto404.backoffice.base.integration.eventBus.InProcessEventBusMiddleware
import com.proyecto404.backoffice.base.infrastructure.persistence.DatabaseInitializer

fun defaultCoreConfig(): Core.Config {
    val eventBus = InProcessEventBus()
    val datasource = com.proyecto404.backoffice.base.data.jdbc.dataSource { dbCredentialsFromEnv() }

    return Core.Config(
        cqBus = CQBus(),
        eventBus,
        dataSource = com.proyecto404.backoffice.base.data.jdbc.dataSource { dbCredentialsFromEnv() },
        databaseInitializer = DatabaseInitializer(datasource.credentials(), runMigrations = true),
        logSql = Env["DB_LOG_SQL"] == "1",
        middlewares = listOf(InProcessEventBusMiddleware(eventBus))
    )
}
