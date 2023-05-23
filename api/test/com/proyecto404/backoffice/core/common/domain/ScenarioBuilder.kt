package com.proyecto404.backoffice.core.common.domain

import com.proyecto404.backoffice.base.data.repositories.RepositoryProvider
import com.proyecto404.backoffice.core.security.app.authorization.Roles
import com.proyecto404.backoffice.core.security.domain.UserExamples
import com.proyecto404.backoffice.core.security.domain.passwords.PasswordEncryptor
import com.proyecto404.backoffice.core.security.domain.user
import com.proyecto404.backoffice.core.security.infrastructure.passwords.PlainPasswordEncryptor

class ScenarioBuilder(
    val repositories: RepositoryProvider,
    private val passwordEncryptor: PasswordEncryptor = PlainPasswordEncryptor()
) {
    val alice by lazy {
        user(repositories) {
            username(UserExamples.alice.username)
            salt(UserExamples.alice.salt)
            encryptedPassword(passwordEncryptor.encrypt(UserExamples.alice.password, UserExamples.alice.salt))
            roles(Roles.Administrative)
            authenticated(UserExamples.alice.sessionToken)
        }
    }
}
