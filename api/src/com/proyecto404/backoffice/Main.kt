package com.proyecto404.backoffice

import com.nbottarini.asimov.environment.Env
import com.proyecto404.backoffice.http.HttpApp
import com.proyecto404.backoffice.http.config.defaultHttpAppConfig

fun main() {
    Env.addSearchPath("./api")
    val http = HttpApp(defaultHttpAppConfig())
    http.start()
}
