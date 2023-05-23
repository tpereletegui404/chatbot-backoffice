package com.proyecto404.backoffice.http.controllers

import com.proyecto404.backoffice.base.http.server.HttpServer
import com.proyecto404.backoffice.base.http.server.controllers.Controller
import com.proyecto404.backoffice.base.http.server.controllers.RouteRegister
import com.proyecto404.backoffice.base.http.server.jsonObj
import com.proyecto404.backoffice.base.integration.application.ApplicationVersion
import io.javalin.http.Context

class StatusController(private val httpServer: HttpServer): Controller {
    override fun registerRoutesIn(http: RouteRegister) {
        http.get("/status", ::status)
    }

    private fun status(ctx: Context) {
        ctx.jsonObj(
            "name" to "chatbot-backoffice api",
            "uid" to httpServer.uid,
            "version" to ApplicationVersion().toString(),
            "status" to "OK",
        )
    }
}
