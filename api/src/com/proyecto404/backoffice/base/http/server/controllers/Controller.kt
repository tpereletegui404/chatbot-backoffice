package com.proyecto404.backoffice.base.http.server.controllers

interface Controller {
    fun registerRoutesIn(http: RouteRegister)
    fun getChildControllers() = emptyList<Controller>()
}
