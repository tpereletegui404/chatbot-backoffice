package com.proyecto404.backoffice.modules.security.domain.sessionToken

import com.proyecto404.backoffice.modules.security.domain.User
import java.util.*

class UUIDSessionTokenFactory: SessionTokenFactory {
    override fun createFor(user: User) = UUID.randomUUID().toString()
}
