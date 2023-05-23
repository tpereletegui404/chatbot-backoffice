package com.proyecto404.backoffice.base.http.server

import io.javalin.http.Context

interface HttpRequestInterceptor {
    fun onRequest(ctx: Context)
}
