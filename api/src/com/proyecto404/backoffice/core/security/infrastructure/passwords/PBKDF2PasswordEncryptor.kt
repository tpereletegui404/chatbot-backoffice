package com.proyecto404.backoffice.core.security.infrastructure.passwords

import com.proyecto404.backoffice.core.security.domain.passwords.PasswordEncryptor
import org.apache.commons.codec.binary.Hex
import java.security.spec.KeySpec
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec

class PBKDF2PasswordEncryptor: PasswordEncryptor {
    override fun encrypt(password: String, salt: String): String {
        val spec: KeySpec = PBEKeySpec(password.toCharArray(), salt.toByteArray(), 65536, 128)
        val factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1")
        val hash = factory.generateSecret(spec).encoded
        return Hex.encodeHexString(hash)
    }
}
