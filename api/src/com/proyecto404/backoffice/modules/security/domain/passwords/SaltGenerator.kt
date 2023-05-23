package com.proyecto404.backoffice.modules.security.domain.passwords

interface SaltGenerator {
    fun generate(): String
}
