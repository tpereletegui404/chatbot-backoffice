package com.proyecto404.backoffice.modules.common.base.http.server

import io.javalin.http.Context

interface HttpRequestInterceptor {
    fun onRequest(ctx: Context)
}
