package com.proyecto404.backoffice.core.security.domain.sessionToken

import com.proyecto404.backoffice.core.security.domain.User

interface SessionTokenFactory {
    fun createFor(user: User): String
}
