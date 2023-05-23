package com.proyecto404.backoffice.modules.security.infrastructure.passwords

import com.proyecto404.backoffice.modules.security.domain.passwords.SaltGenerator

class FixedSaltGenerator(private val salt: String): SaltGenerator {
    override fun generate() = salt
}
