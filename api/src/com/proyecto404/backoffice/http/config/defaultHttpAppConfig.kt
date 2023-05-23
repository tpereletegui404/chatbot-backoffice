package com.proyecto404.backoffice.http.config

import com.nbottarini.asimov.environment.Env
import com.proyecto404.backoffice.http.HttpApp
import com.proyecto404.backoffice.Core
import com.proyecto404.backoffice.base.http.server.HttpServer
import com.proyecto404.backoffice.base.infrastructure.config.defaultCoreConfig

fun defaultHttpAppConfig(): HttpApp.Config {
    val httpServer = HttpServer(Env["PORT"]?.toInt() ?: 8080)
    return HttpApp.Config(
        httpServer = httpServer,
        core = Core(defaultCoreConfig()),
    )
}
