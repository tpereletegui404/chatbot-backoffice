package com.proyecto404.backoffice.http.controllers

import com.proyecto404.backoffice.core.app.AddContext
import com.proyecto404.backoffice.base.http.server.controllers.CQController
import com.proyecto404.backoffice.base.http.server.controllers.RouteRegister
import com.proyecto404.backoffice.base.http.server.httpCQDispatcher.HttpCQDispatcher

class ChatbotController(dispatcher: HttpCQDispatcher): CQController(dispatcher) {
    override fun registerRoutesIn(http: RouteRegister) {
        post<AddContext>(http, "/context", 200)
    }
}
