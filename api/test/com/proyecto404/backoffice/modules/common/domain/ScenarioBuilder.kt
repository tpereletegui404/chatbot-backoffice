package com.proyecto404.backoffice.modules.common.domain

import com.proyecto404.backoffice.modules.common.base.data.repositories.RepositoryProvider
import com.proyecto404.backoffice.modules.security.app.authorization.Roles
import com.proyecto404.backoffice.modules.security.domain.UserExamples
import com.proyecto404.backoffice.modules.security.domain.passwords.PasswordEncryptor
import com.proyecto404.backoffice.modules.security.domain.user
import com.proyecto404.backoffice.modules.security.infrastructure.passwords.PlainPasswordEncryptor

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
