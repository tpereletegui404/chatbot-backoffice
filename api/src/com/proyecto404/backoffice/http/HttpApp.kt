package com.proyecto404.backoffice.http

import com.google.gson.JsonParseException
import com.proyecto404.backoffice.Core
import com.proyecto404.backoffice.base.auth.NotAuthenticatedError
import com.proyecto404.backoffice.base.auth.UnauthorizedAccessError
import com.proyecto404.backoffice.base.domain.errors.DomainError
import com.proyecto404.backoffice.base.domain.errors.NotFoundError
import com.proyecto404.backoffice.base.http.server.HttpServer
import com.proyecto404.backoffice.base.http.server.httpCQDispatcher.HttpCQDispatcher
import com.proyecto404.backoffice.http.controllers.ChatbotController
import com.proyecto404.backoffice.http.controllers.SecurityController
import com.proyecto404.backoffice.http.controllers.StatusController

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
        ChatbotController(requestDispatcher),
    )

    private fun registerExceptionHandlers() = with(httpServer) {
        registerException<NotAuthenticatedError>(::notAuthenticatedErrorHandler)
        registerException<UnauthorizedAccessError>(::forbiddenErrorHandler)
        registerException<NotFoundError>(::notFoundErrorHandler)
        registerException<DomainError>(::badRequestJsonErrorHandler)
        registerException<JsonParseException>(::badRequestJsonErrorHandler)
    }

    private fun registerMiddlewares() {
//        config.core.registerMiddleware(SessionTokenAuthenticationMiddleware())
    }

    fun start() = httpServer.start()

    fun stop() = httpServer.stop()

    data class Config(
        val httpServer: HttpServer,
        val core: Core,
    )
}
