package com.proyecto404.backoffice.core.security.domain

import com.proyecto404.backoffice.core.security.domain.sessionToken.SessionTokenFactory

class FixedSessionTokenFactory(private var sessionToken: String): SessionTokenFactory {
    fun willCreate(sessionToken: String) {
        this.sessionToken = sessionToken
    }

    override fun createFor(user: User) = sessionToken
}
