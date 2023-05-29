package com.proyecto404.backoffice.http.controllers

import com.proyecto404.backoffice.base.http.server.controllers.CQController
import com.proyecto404.backoffice.base.http.server.controllers.RouteRegister
import com.proyecto404.backoffice.base.http.server.httpCQDispatcher.HttpCQDispatcher
import com.proyecto404.backoffice.core.app.AddContext
import com.proyecto404.backoffice.core.app.CreateConfiguration
import com.proyecto404.backoffice.core.app.GetConfiguration
import com.proyecto404.backoffice.core.app.UpdateConfiguration

class ChatbotController(dispatcher: HttpCQDispatcher): CQController(dispatcher) {
    override fun registerRoutesIn(http: RouteRegister) {
        post<AddContext>(http, "/configurations/context", 200)
        get<GetConfiguration>(http, "/configurations")
        post<CreateConfiguration>(http, "/configurations", 201)
        put<UpdateConfiguration>(http, "/configurations")
    }
}
