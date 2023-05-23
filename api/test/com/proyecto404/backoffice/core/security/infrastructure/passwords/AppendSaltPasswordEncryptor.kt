package com.proyecto404.backoffice.core.security.infrastructure.passwords

import com.proyecto404.backoffice.core.security.domain.passwords.PasswordEncryptor

class AppendSaltPasswordEncryptor: PasswordEncryptor {
    override fun encrypt(password: String, salt: String) = password + salt
}
