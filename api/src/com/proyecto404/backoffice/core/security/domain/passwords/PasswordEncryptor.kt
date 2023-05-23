package com.proyecto404.backoffice.core.security.domain.passwords

interface PasswordEncryptor {
    fun encrypt(password: String, salt: String): String
}
