package com.proyecto404.backoffice.base.http.server.controllers

import io.javalin.Javalin
import io.javalin.http.Handler
import io.javalin.http.HandlerType

class RouteRegister(private val app: Javalin) {
    fun before(handler: Handler): RouteRegister {
        app.before(handler)
        return this
    }

    fun post(path: String, handler: Handler): RouteRegister {
        registerRoute(HandlerType.POST, path, handler)
        return this
    }

    fun get(path: String, handler: Handler): RouteRegister {
        registerRoute(HandlerType.GET, path, handler)
        return this
    }

    fun put(path: String, handler: Handler): RouteRegister {
        registerRoute(HandlerType.PUT, path, handler)
        return this
    }

    fun patch(path: String, handler: Handler): RouteRegister {
        registerRoute(HandlerType.PATCH, path, handler)
        return this
    }

    fun delete(path: String, handler: Handler): RouteRegister {
        registerRoute(HandlerType.DELETE, path, handler)
        return this
    }

    private fun registerRoute(verb: HandlerType, path: String, handler: Handler) {
        app.addHandler(verb, path, handler)
    }
}
