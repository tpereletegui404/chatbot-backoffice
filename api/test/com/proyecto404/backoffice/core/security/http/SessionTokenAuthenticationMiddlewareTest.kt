package com.proyecto404.backoffice.core.security.http

import com.nbottarini.asimov.cqbus.ExecutionContext
import com.nbottarini.asimov.cqbus.requests.PureCommand
import com.proyecto404.backoffice.core.security.domain.AuthExamples.sessionTokens
import io.javalin.http.Context
import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import javax.servlet.http.HttpServletRequest

class SessionTokenAuthenticationMiddlewareTest {
    @Test
    fun `save session token in execution context if Authorization header is present`() {
        every { httpRequest.getHeader("Authorization") } returns "Bearer $SESSION_TOKEN"

        middleware.execute(SomeCommand(), {}, executionContext)

        assertThat(executionContext["SessionToken"]).isEqualTo(SESSION_TOKEN)
    }

    @ParameterizedTest
    @ValueSource(strings = ["", "SOME_SESSION", "Bearer", "Bearer "])
    fun `don't save session token if Authorization header is invalid `(header: String) {
        every { httpRequest.getHeader("Authorization") } returns header

        middleware.execute(SomeCommand(), {}, executionContext)

        assertThat(executionContext["SessionToken"]).isNull()
    }

    @BeforeEach
    fun beforeEach() {
        every { httpRequest.getHeader(any()) } returns ""
        executionContext.set(httpContext)
    }

    private val SESSION_TOKEN = sessionTokens.one()
    private val executionContext = ExecutionContext()
    private val httpRequest = mockk<HttpServletRequest>()
    private val httpContext = Context(httpRequest, mockk())
    private val middleware = SessionTokenAuthenticationMiddleware()

    private class SomeCommand: PureCommand
}
