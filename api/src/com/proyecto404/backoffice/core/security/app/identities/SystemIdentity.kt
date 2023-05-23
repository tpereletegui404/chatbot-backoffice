package com.proyecto404.backoffice.core.security.app.identities

import com.nbottarini.asimov.cqbus.identity.Identity
import com.proyecto404.backoffice.core.security.app.authorization.Roles

class SystemIdentity: Identity {
    override val authenticationType: String? = null
    override val isAuthenticated = true
    override val name = "system"
    override val properties = mapOf<String, Any>()
    override val roles = listOf(Roles.System.name)
}
