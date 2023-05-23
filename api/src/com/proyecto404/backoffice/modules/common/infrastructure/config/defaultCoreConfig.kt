package com.proyecto404.backoffice.modules.common.infrastructure.config

import com.nbottarini.asimov.cqbus.CQBus
import com.nbottarini.asimov.environment.Env
import com.proyecto404.backoffice.modules.common.Core
import com.proyecto404.backoffice.modules.common.base.data.jdbc.dataSource
import com.proyecto404.backoffice.modules.common.base.integration.eventBus.InProcessEventBus
import com.proyecto404.backoffice.modules.common.base.integration.eventBus.InProcessEventBusMiddleware
import com.proyecto404.backoffice.modules.common.infrastructure.persistence.DatabaseInitializer

fun defaultCoreConfig(): Core.Config {
    val eventBus = InProcessEventBus()
    val datasource = dataSource { dbCredentialsFromEnv() }

    return Core.Config(
        cqBus = CQBus(),
        eventBus,
        dataSource = dataSource { dbCredentialsFromEnv() },
        databaseInitializer = DatabaseInitializer(datasource.credentials(), runMigrations = true),
        logSql = Env["DB_LOG_SQL"] == "1",
        middlewares = listOf(InProcessEventBusMiddleware(eventBus))
    )
}
