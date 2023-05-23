package com.proyecto404.backoffice.core.security.domain

import com.nbottarini.asimov.lang.DetailsExt
import com.nbottarini.asimov.lang.extensions.ifNotNull
import com.proyecto404.backoffice.base.data.repositories.RepositoryProvider
import com.proyecto404.backoffice.base.domain.Id
import com.proyecto404.backoffice.base.testing.PersistentBuilder
import com.proyecto404.backoffice.core.common.domain.ScenarioBuilder
import com.proyecto404.backoffice.core.security.app.authorization.Roles
import com.proyecto404.backoffice.core.security.domain.AuthExamples.sessionTokens
import com.proyecto404.backoffice.core.security.domain.UserExamples.usernames

private var nextId = 1

class UserBuilder(private val repositories: RepositoryProvider? = null): PersistentBuilder<User>() {
    private var id = repositories?.get<Users>()?.nextId() ?: Id(nextId++)
    private var username = usernames.one()
    private var sessionToken: String? = null
    private var salt = "salt"
    private var encryptedPassword = "1234"
    private var roles: Set<Roles> = setOf()

    fun username(value: String) = apply { this.username = value }

    fun authenticated(sessionToken: String = sessionTokens.one()) = apply { this.sessionToken = sessionToken }

    fun roles(vararg value: Roles) = apply { this.roles = value.toSet() }

    fun salt(value: String) = apply { salt = value }

    fun encryptedPassword(value: String) = apply { encryptedPassword = value }

    override fun doBuild(): User {
        val user = User(id, username, encryptedPassword, salt, roles)
        sessionToken.ifNotNull { user.sessionToken = it }
        return user
    }

    override fun doSave(obj: User) {
        repositories?.get<Users>()?.add(obj)
    }
}

fun user(repositories: RepositoryProvider? = null, addDetails: DetailsExt<UserBuilder> = {}) =
    UserBuilder(repositories).also(addDetails).build()

fun user(addDetails: DetailsExt<UserBuilder> = {}) = user(null, addDetails)

fun ScenarioBuilder.user(addDetails: DetailsExt<UserBuilder> = {}) = user(repositories, addDetails)
