package com.proyecto404.backoffice.core.security.infrastructure.middlewares

import com.nbottarini.asimov.cqbus.ExecutionContext
import com.nbottarini.asimov.cqbus.requests.PureCommand
import com.proyecto404.backoffice.core.common.domain.ScenarioBuilder
import com.proyecto404.backoffice.base.infrastructure.persistence.memory.MemoryRepositoryProvider
import com.proyecto404.backoffice.core.security.app.authorization.Roles
import com.proyecto404.backoffice.core.security.app.identities.UserIdentity
import com.proyecto404.backoffice.core.security.domain.AuthExamples.aliceSessionToken
import com.proyecto404.backoffice.core.security.domain.user
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class AuthenticationMiddlewareTest {
    @Test
    fun `authenticate with session token if present in execution context`() {
        val alice = scenario.user { authenticated(aliceSessionToken); roles(Roles.Administrative) }
        executionContext["SessionToken"] = aliceSessionToken

        middleware.execute(SomeCommand(), {}, executionContext)

        val userIdentity = executionContext.identity as UserIdentity
        assertThat(userIdentity.user.id).isEqualTo(alice.id)
        assertThat(userIdentity.isAuthenticated).isTrue
        assertThat(userIdentity.roles).contains(Roles.Administrative.name)
    }

    private val executionContext = ExecutionContext()
    private val repositories = MemoryRepositoryProvider()
    private val middleware = AuthenticationMiddleware(repositories.get())
    private val scenario = ScenarioBuilder(repositories)

    private class SomeCommand: PureCommand
}
