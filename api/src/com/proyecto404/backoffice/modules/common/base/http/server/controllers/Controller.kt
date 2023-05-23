package com.proyecto404.backoffice.modules.common.base.http.server.controllers

interface Controller {
    fun registerRoutesIn(http: RouteRegister)
    fun getChildControllers() = emptyList<Controller>()
}
