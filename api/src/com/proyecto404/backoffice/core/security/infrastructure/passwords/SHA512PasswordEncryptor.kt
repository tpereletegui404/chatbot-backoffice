package com.proyecto404.backoffice.core.security.infrastructure.passwords

import com.proyecto404.backoffice.core.security.domain.passwords.PasswordEncryptor
import org.apache.commons.codec.binary.Hex
import java.security.MessageDigest

class SHA512PasswordEncryptor: PasswordEncryptor {
    override fun encrypt(password: String, salt: String): String {
        val md = MessageDigest.getInstance("SHA-512")
        val hash = md.digest((password + salt).toByteArray())
        return Hex.encodeHexString(hash)
    }
}
