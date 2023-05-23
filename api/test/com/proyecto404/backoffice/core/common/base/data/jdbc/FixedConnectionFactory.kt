package com.proyecto404.backoffice.base.data.jdbc

import com.proyecto404.backoffice.base.data.jdbc.connectionFactory.JdbcConnectionFactory
import java.io.PrintWriter
import java.sql.Connection

class FixedConnectionFactory(private val connection: Connection): JdbcConnectionFactory {
    override var loginTimeoutSecs = 0
    override var logWriter: PrintWriter? = null
    override val databaseDriver = "testDriver"

    override fun getConnection() = connection
}
