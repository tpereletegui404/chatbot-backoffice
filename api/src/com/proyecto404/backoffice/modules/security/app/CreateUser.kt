package com.proyecto404.backoffice.modules.security.app

import com.nbottarini.asimov.cqbus.identity.Identity
import com.nbottarini.asimov.cqbus.requests.Command
import com.nbottarini.asimov.cqbus.requests.handlers.RequestHandler
import com.proyecto404.backoffice.modules.common.base.data.repositories.RepositoryProvider
import com.proyecto404.backoffice.modules.common.base.domain.Id
import com.proyecto404.backoffice.modules.common.base.integration.application.CreateResponse
import com.proyecto404.backoffice.modules.security.app.authorization.Authorization
import com.proyecto404.backoffice.modules.security.app.authorization.Roles
import com.proyecto404.backoffice.modules.security.app.authorization.Roles.Admin
import com.proyecto404.backoffice.modules.security.domain.User
import com.proyecto404.backoffice.modules.security.domain.passwords.PasswordEncryptor
import com.proyecto404.backoffice.modules.security.domain.passwords.SaltGenerator
import com.proyecto404.backoffice.modules.security.domain.users

@Authorization([Admin])
data class CreateUser(
    val username: String,
    val password: String,
    val roles: Set<Roles> = setOf(),
): Command<CreateResponse<User>> {
    internal class Handler(
        private val repositories: RepositoryProvider,
        private val saltGenerator: SaltGenerator,
        private val passwordEncryptor: PasswordEncryptor,
    ): RequestHandler<CreateUser, CreateResponse<User>> {

        override fun execute(request: CreateUser, identity: Identity): CreateResponse<User> {
            val id = repositories.users().nextId()
            val user = userFromRequest(id, request)
            repositories.users().add(user)
            return CreateResponse(id)
        }

        private fun userFromRequest(id: Id<User>, request: CreateUser): User {
            val salt = saltGenerator.generate()
            val encryptedPassword = passwordEncryptor.encrypt(request.password, salt)
            return User(
                id,
                request.username,
                encryptedPassword,
                salt,
                request.roles,
            )
        }
    }
}
