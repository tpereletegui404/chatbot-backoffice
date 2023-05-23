package com.proyecto404.backoffice.core.security.app

import com.nbottarini.asimov.cqbus.identity.Identity
import com.nbottarini.asimov.cqbus.requests.Command
import com.nbottarini.asimov.cqbus.requests.handlers.RequestHandler
import com.proyecto404.backoffice.base.data.repositories.RepositoryProvider
import com.proyecto404.backoffice.base.domain.Id
import com.proyecto404.backoffice.core.security.app.Login.LoginResponse
import com.proyecto404.backoffice.core.security.domain.User
import com.proyecto404.backoffice.core.security.domain.passwords.PasswordEncryptor
import com.proyecto404.backoffice.core.security.domain.sessionToken.SessionTokenFactory
import com.proyecto404.backoffice.core.security.domain.users

data class Login(val username: String, val password: String): Command<LoginResponse> {
    internal class Handler(
        private val repositories: RepositoryProvider,
        private val passwordEncryptor: PasswordEncryptor,
        private val sessionTokenFactory: SessionTokenFactory,
    ): RequestHandler<Login, LoginResponse> {

        override fun execute(request: Login, identity: Identity): LoginResponse {
            val user = repositories.users().findByUsername(request.username) ?: return LoginResponse.failure()
            if (!user.isValidPassword(passwordEncryptor, request.password)) return LoginResponse.failure()
            user.sessionToken = sessionTokenFactory.createFor(user)
            repositories.users().update(user)
            return LoginResponse.successful(user)
        }
    }

    data class LoginResponse(val isSuccessful: Boolean, val sessionToken: String? = null, val user: UserData? = null) {
        companion object {
            fun successful(user: User) = LoginResponse(true, user.sessionToken!!, UserData(user.id, user.username))

            fun failure() = LoginResponse(false)
        }
    }

    data class UserData(val id: Id<User>, val username: String)
}
