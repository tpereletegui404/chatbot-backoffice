package com.proyecto404.backoffice.modules.security

import com.proyecto404.backoffice.modules.common.Core
import com.proyecto404.backoffice.modules.security.app.CreateUser
import com.proyecto404.backoffice.modules.security.app.Login
import com.proyecto404.backoffice.modules.security.domain.sessionToken.UUIDSessionTokenFactory
import com.proyecto404.backoffice.modules.security.infrastructure.middlewares.AuthenticationMiddleware
import com.proyecto404.backoffice.modules.security.infrastructure.middlewares.AuthorizationMiddleware
import com.proyecto404.backoffice.modules.security.infrastructure.passwords.RandomSaltGenerator
import com.proyecto404.backoffice.modules.security.infrastructure.passwords.SHA512PasswordEncryptor

class SecurityModule(coreConfig: Core.Config, coreServices: Core.Services) {
    private val cqBus = coreServices.cqBus
    private val repositories = coreServices.repositories
    private val saltGenerator = RandomSaltGenerator()
    private val passwordEncryptor = SHA512PasswordEncryptor()
    private val sessionTokenFactory = UUIDSessionTokenFactory()

    init {
        with(cqBus) {
            registerMiddleware(AuthorizationMiddleware())
            registerMiddleware(AuthenticationMiddleware(repositories.get()))

            registerHandler { CreateUser.Handler(repositories, saltGenerator, passwordEncryptor) }
            registerHandler { Login.Handler(repositories, passwordEncryptor, sessionTokenFactory) }
        }
    }
}
