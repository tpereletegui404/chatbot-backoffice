package com.proyecto404.backoffice.modules.security.infrastructure.passwords

import com.proyecto404.backoffice.modules.security.domain.passwords.SaltGenerator
import org.apache.commons.codec.binary.Hex
import java.security.SecureRandom

class RandomSaltGenerator: SaltGenerator {
    override fun generate(): String {
        val random = SecureRandom()
        val salt = ByteArray(16)
        random.nextBytes(salt)
        return Hex.encodeHexString(salt)
    }
}
