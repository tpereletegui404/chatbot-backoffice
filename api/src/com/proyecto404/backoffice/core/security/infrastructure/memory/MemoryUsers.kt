package com.proyecto404.backoffice.core.security.infrastructure.memory

import com.proyecto404.backoffice.base.domain.Id
import com.proyecto404.backoffice.core.security.domain.User
import com.proyecto404.backoffice.core.security.domain.Users

class MemoryUsers: Users {
    private var nextId = 1
    private val items = mutableListOf<User>()

    override fun nextId(): Id<User> = Id(nextId++)

    override fun add(user: User) {
        items.add(user)
    }

    override fun update(user: User) {}

    override fun findBy(sessionToken: String) = items.singleOrNull { it.sessionToken == sessionToken }

    override fun findByUsername(username: String) = items.singleOrNull { it.username == username }

    override fun get(id: Id<User>) = items.single { it.id == id }
}
