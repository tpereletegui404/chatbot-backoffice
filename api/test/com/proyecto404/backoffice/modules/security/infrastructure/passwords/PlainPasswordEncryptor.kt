package com.proyecto404.backoffice.modules.security.infrastructure.passwords

import com.proyecto404.backoffice.modules.security.domain.passwords.PasswordEncryptor

class PlainPasswordEncryptor: PasswordEncryptor {
    override fun encrypt(password: String, salt: String) = password
}
