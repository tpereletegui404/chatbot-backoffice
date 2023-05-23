package com.proyecto404.backoffice.modules.security.domain.sessionToken

import com.proyecto404.backoffice.modules.security.domain.User

interface SessionTokenFactory {
    fun createFor(user: User): String
}
