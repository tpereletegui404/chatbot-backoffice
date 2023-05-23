package com.proyecto404.backoffice.core.security.infrastructure.jooq

import com.proyecto404.backoffice.base.data.jooq.JooqConfiguration
import com.proyecto404.backoffice.base.domain.Id
import com.proyecto404.backoffice.core.security.domain.User
import com.proyecto404.backoffice.core.security.domain.UserAlreadyExistsError
import com.proyecto404.backoffice.core.security.domain.Users
import com.proyecto404.backoffice.base.infrastructure.serialization.JsonSerializer
import org.jooq.exception.DataAccessException

class JooqUsers(val jooq: JooqConfiguration): Users {
    private val serializer = JsonSerializer()

    override fun nextId(): Id<User> {
        TODO("Not yet implemented")
    }

    override fun get(id: Id<User>) =
        TODO()

    override fun add(user: User) {
        TODO()
    }

    override fun update(user: User) {
        TODO()
    }

    override fun findBy(sessionToken: String) = TODO()

    override fun findByUsername(username: String) = TODO()

    private fun <T> captureKnownErrors(block: () -> T): T {
        try {
            return block()
        } catch (e: DataAccessException) {
            if (e.message?.contains("users_username_uq") == true) throw UserAlreadyExistsError()
            throw e
        }
    }
}
