package com.proyecto404.backoffice.modules.security.domain.passwords

interface PasswordEncryptor {
    fun encrypt(password: String, salt: String): String
}
