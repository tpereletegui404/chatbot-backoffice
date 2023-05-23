package com.proyecto404.backoffice.base.data.jdbc.connectionFactory

import java.io.PrintWriter
import java.sql.Connection

interface JdbcConnectionFactory {
    val databaseDriver: String
    var loginTimeoutSecs: Int
    var logWriter: PrintWriter?

    fun getConnection(): Connection
}
