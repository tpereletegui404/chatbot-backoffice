package com.proyecto404.backoffice.modules.common.base.data.jdbc

import com.proyecto404.backoffice.modules.common.base.data.jdbc.ConnectionStub
import com.proyecto404.backoffice.modules.common.base.data.jdbc.SavePoints
import com.proyecto404.backoffice.modules.common.base.data.jdbc.connectionFactory.JdbcConnectionFactory
import java.io.PrintWriter

class StubbedConnectionFactory(private val savePoints: SavePoints = SavePoints()): JdbcConnectionFactory {
    override var loginTimeoutSecs = 0
    override var logWriter: PrintWriter? = null
    override val databaseDriver = "testDriver"

    override fun getConnection() = ConnectionStub(savePoints)
}
