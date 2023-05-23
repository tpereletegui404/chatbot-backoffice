package com.proyecto404.backoffice.modules.common.infrastructure.persistence

import com.proyecto404.backoffice.modules.common.base.data.jdbc.connectionFactory.credentials.JdbcCredentials
import org.flywaydb.core.Flyway

class DatabaseInitializer(private val jdbcCredentials: JdbcCredentials, private val runMigrations: Boolean) {
    fun run() {
        val flyway = Flyway.configure()
            .dataSource(jdbcCredentials.url.toString(), jdbcCredentials.userName, jdbcCredentials.password)
            .cleanDisabled(false)
            .locations("classpath:db")
            .load()

        if (runMigrations) flyway.migrate()
    }
}
