package com.proyecto404.backoffice.core.security.domain.sessionToken

import com.proyecto404.backoffice.core.security.domain.User
import java.util.*

class UUIDSessionTokenFactory: SessionTokenFactory {
    override fun createFor(user: User) = UUID.randomUUID().toString()
}
