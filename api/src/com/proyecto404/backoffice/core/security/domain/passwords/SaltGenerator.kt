package com.proyecto404.backoffice.core.security.domain.passwords

interface SaltGenerator {
    fun generate(): String
}
