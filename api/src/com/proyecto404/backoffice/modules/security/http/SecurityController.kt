package com.proyecto404.backoffice.modules.security.http

import com.proyecto404.backoffice.modules.common.base.http.server.controllers.CQController
import com.proyecto404.backoffice.modules.common.base.http.server.controllers.RouteRegister
import com.proyecto404.backoffice.modules.common.base.http.server.httpCQDispatcher.HttpCQDispatcher
import com.proyecto404.backoffice.modules.security.app.CreateUser
import com.proyecto404.backoffice.modules.security.app.Login

class SecurityController(dispatcher: HttpCQDispatcher): CQController(dispatcher) {
    override fun registerRoutesIn(http: RouteRegister) {
        post<CreateUser>(http, "/users", 201)
        post<Login>(http, "/login")
    }
}
