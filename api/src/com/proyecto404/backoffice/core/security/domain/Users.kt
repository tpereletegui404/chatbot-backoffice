package com.proyecto404.backoffice.core.security.domain

import com.proyecto404.backoffice.base.data.repositories.RepositoryProvider
import com.proyecto404.backoffice.base.domain.Id

interface Users {
    fun nextId(): Id<User>
    fun get(id: Id<User>): User
    fun add(user: User)
    fun findBy(sessionToken: String): User?
    fun findByUsername(username: String): User?
    fun update(user: User)
}

fun RepositoryProvider.users() = this.get<Users>()

