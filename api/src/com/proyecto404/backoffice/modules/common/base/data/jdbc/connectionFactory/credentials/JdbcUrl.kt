package com.proyecto404.backoffice.modules.common.base.data.jdbc.connectionFactory.credentials

class JdbcUrl(val driver: String, val host: String, val port: Int, val name: String) {
    override fun toString(): String {
        return "jdbc:$driver://$host:$port/$name"
    }
}
