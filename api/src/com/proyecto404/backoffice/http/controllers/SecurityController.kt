package com.proyecto404.backoffice.http.controllers

import com.proyecto404.backoffice.base.http.server.controllers.CQController
import com.proyecto404.backoffice.base.http.server.controllers.RouteRegister
import com.proyecto404.backoffice.base.http.server.httpCQDispatcher.HttpCQDispatcher
import com.proyecto404.backoffice.core.security.app.CreateUser
import com.proyecto404.backoffice.core.security.app.Login

class SecurityController(dispatcher: HttpCQDispatcher): CQController(dispatcher) {
    override fun registerRoutesIn(http: RouteRegister) {
        post<CreateUser>(http, "/users", 201)
        post<Login>(http, "/login")
    }
}
