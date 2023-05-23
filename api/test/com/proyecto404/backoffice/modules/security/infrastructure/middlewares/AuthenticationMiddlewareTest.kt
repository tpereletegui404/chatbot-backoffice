package com.proyecto404.backoffice.modules.security.infrastructure.middlewares

import com.nbottarini.asimov.cqbus.ExecutionContext
import com.nbottarini.asimov.cqbus.requests.PureCommand
import com.proyecto404.backoffice.modules.common.domain.ScenarioBuilder
import com.proyecto404.backoffice.modules.common.infrastructure.persistence.memory.MemoryRepositoryProvider
import com.proyecto404.backoffice.modules.security.app.authorization.Roles
import com.proyecto404.backoffice.modules.security.app.identities.UserIdentity
import com.proyecto404.backoffice.modules.security.domain.AuthExamples.aliceSessionToken
import com.proyecto404.backoffice.modules.security.domain.user
import com.proyecto404.backoffice.modules.security.infrastructure.middlewares.AuthenticationMiddleware
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
