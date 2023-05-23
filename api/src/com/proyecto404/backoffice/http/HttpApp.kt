package com.proyecto404.backoffice.http

import com.google.gson.JsonParseException
import com.proyecto404.backoffice.modules.common.Core
import com.proyecto404.backoffice.modules.common.base.auth.NotAuthenticatedError
import com.proyecto404.backoffice.modules.common.base.auth.UnauthorizedAccessError
import com.proyecto404.backoffice.modules.common.base.domain.errors.DomainError
import com.proyecto404.backoffice.modules.common.base.domain.errors.NotFoundError
import com.proyecto404.backoffice.modules.common.base.http.server.HttpServer
import com.proyecto404.backoffice.modules.common.base.http.server.httpCQDispatcher.HttpCQDispatcher
import com.proyecto404.backoffice.modules.common.http.StatusController
import com.proyecto404.backoffice.modules.security.http.SecurityController
import com.proyecto404.backoffice.modules.security.http.SessionTokenAuthenticationMiddleware

class HttpApp(private val config: Config) {
    private val httpServer = config.httpServer
    private val requestDispatcher = HttpCQDispatcher(config.core)

    init {
        registerRequestToJsonTransformers()
        registerControllers()
        registerExceptionHandlers()
        registerMiddlewares()
    }

    private fun registerRequestToJsonTransformers() {
    }

    private fun registerControllers() = httpServer.registerControllers(
        StatusController(httpServer),
        SecurityController(requestDispatcher),
    )

    private fun registerExceptionHandlers() = with(httpServer) {
        registerException<NotAuthenticatedError>(::notAuthenticatedErrorHandler)
        registerException<UnauthorizedAccessError>(::forbiddenErrorHandler)
        registerException<NotFoundError>(::notFoundErrorHandler)
        registerException<DomainError>(::badRequestJsonErrorHandler)
        registerException<JsonParseException>(::badRequestJsonErrorHandler)
    }

    private fun registerMiddlewares() {
        config.core.registerMiddleware(SessionTokenAuthenticationMiddleware())
    }

    fun start() = httpServer.start()

    fun stop() = httpServer.stop()

    data class Config(
        val httpServer: HttpServer,
        val core: Core,
    )
}
