package com.proyecto404.backoffice.base.data.jooq

import org.jooq.SQLDialect
import java.util.*

fun createSQLDialectFromDriver(jdbcDriver: String): SQLDialect {
    return when(jdbcDriver.lowercase(Locale.getDefault())) {
        "postgresql" -> SQLDialect.POSTGRES
        "mysql" -> SQLDialect.MYSQL
        else -> throw Exception("No configured dialect for $jdbcDriver")
    }
}
