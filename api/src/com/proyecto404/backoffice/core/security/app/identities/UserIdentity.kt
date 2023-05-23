package com.proyecto404.backoffice.core.security.app.identities

import com.nbottarini.asimov.cqbus.identity.Identity
import com.proyecto404.backoffice.core.security.domain.User

class UserIdentity(val user: User): Identity {
    override val authenticationType: String? = null
    override val isAuthenticated = true
    override val name = user.username
    override val properties = mapOf<String, Any>()
    override val roles = user.roles.map { it.name }
}
