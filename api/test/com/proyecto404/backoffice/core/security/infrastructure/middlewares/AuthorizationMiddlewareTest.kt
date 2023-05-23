package com.proyecto404.backoffice.core.security.infrastructure.middlewares

import com.nbottarini.asimov.cqbus.ExecutionContext
import com.nbottarini.asimov.cqbus.identity.Identity
import com.nbottarini.asimov.cqbus.requests.PureCommand
import com.proyecto404.backoffice.core.security.app.authorization.Authorization
import com.proyecto404.backoffice.core.security.app.authorization.Roles
import com.proyecto404.backoffice.base.auth.UnauthorizedAccessError
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows

class AuthorizationMiddlewareTest {
    @Test
    fun `fail if required role is not present in authentication info`() {
        executionContext.identity = SomeIdentity(roles = listOf("not-admin"))

        assertThrows<com.proyecto404.backoffice.base.auth.UnauthorizedAccessError> {
            middleware.execute(SomeRestrictedToAdminCommand(), {}, executionContext)
        }
    }

    @Test
    fun `fail if requires roles and is not authenticated`() {
        assertThrows<com.proyecto404.backoffice.base.auth.UnauthorizedAccessError> {
            middleware.execute(SomeRestrictedToAdminCommand(), {}, executionContext)
        }
    }

    @Test
    fun `don't fail if not authentication is required`() {
        assertDoesNotThrow {
            middleware.execute(SomePublicCommand(), {}, executionContext)
        }
    }

    @Test
    fun `don't fail if authenticated with any of required roles`() {
        executionContext.identity = SomeIdentity(roles = listOf("member"))
        assertDoesNotThrow {
            middleware.execute(SomeRestrictedToAdminAndMemberCommand(), {}, executionContext)
        }
    }

    private val executionContext = ExecutionContext()
    private val middleware = AuthorizationMiddleware()

    private class SomeIdentity(override val roles: List<String>): Identity {
        override val authenticationType: String? = null
        override val isAuthenticated = true
        override val name = "Some Identity"
        override val properties = mapOf<String, Any>()
    }

    private class SomePublicCommand: PureCommand

    @Authorization(roles = [Roles.Admin])
    private class SomeRestrictedToAdminCommand: PureCommand

    @Authorization(roles = [Roles.Admin, Roles.Member])
    private class SomeRestrictedToAdminAndMemberCommand: PureCommand
}
