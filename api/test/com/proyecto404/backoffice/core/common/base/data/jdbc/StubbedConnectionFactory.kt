package com.proyecto404.backoffice.base.data.jdbc

import com.proyecto404.backoffice.base.data.jdbc.connectionFactory.JdbcConnectionFactory
import java.io.PrintWriter

class StubbedConnectionFactory(private val savePoints: SavePoints = SavePoints()): JdbcConnectionFactory {
    override var loginTimeoutSecs = 0
    override var logWriter: PrintWriter? = null
    override val databaseDriver = "testDriver"

    override fun getConnection() = ConnectionStub(savePoints)
}
