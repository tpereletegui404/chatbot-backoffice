package com.proyecto404.backoffice.modules.common.http

import com.proyecto404.backoffice.modules.common.base.http.server.HttpServer
import com.proyecto404.backoffice.modules.common.base.http.server.controllers.Controller
import com.proyecto404.backoffice.modules.common.base.http.server.controllers.RouteRegister
import com.proyecto404.backoffice.modules.common.base.http.server.jsonObj
import com.proyecto404.backoffice.modules.common.base.integration.application.ApplicationVersion
import io.javalin.http.Context

class StatusController(private val httpServer: HttpServer): Controller {
    override fun registerRoutesIn(http: RouteRegister) {
        http.get("/status", ::status)
    }

    private fun status(ctx: Context) {
        ctx.jsonObj(
            "name" to "finanz api",
            "uid" to httpServer.uid,
            "version" to ApplicationVersion().toString(),
            "status" to "OK",
        )
    }
}
