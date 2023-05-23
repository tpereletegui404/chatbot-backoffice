package com.proyecto404.backoffice.base.data.jdbc.connectionFactory

import com.proyecto404.backoffice.base.data.jdbc.connectionFactory.credentials.JdbcCredentials
import java.io.PrintWriter
import java.sql.Connection
import java.sql.DriverManager

class SimpleJdbcConnectionFactory(private val credentials: JdbcCredentials): JdbcConnectionFactory {
    override val databaseDriver = credentials.url.driver

    override fun getConnection(): Connection {
        return DriverManager.getConnection(credentials.url.toString(), credentials.userName, credentials.password)
    }

    override var logWriter: PrintWriter?
        get() = DriverManager.getLogWriter()
        set(value) { DriverManager.setLogWriter(value) }

    override var loginTimeoutSecs: Int
        get() = DriverManager.getLoginTimeout()
        set(value) { DriverManager.setLoginTimeout(value) }
}
