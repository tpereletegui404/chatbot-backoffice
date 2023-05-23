package com.proyecto404.backoffice.modules.common.base.http.server

import com.nbottarini.asimov.json.parser.JsonParseError
import com.proyecto404.backoffice.modules.common.base.http.server.controllers.Controller
import com.proyecto404.backoffice.modules.common.base.http.server.controllers.RouteRegister
import com.proyecto404.backoffice.modules.common.base.http.server.controllers.errors.ParameterError
import com.proyecto404.backoffice.modules.common.base.http.server.stats.*
import io.javalin.Javalin
import io.javalin.http.Context
import io.javalin.http.ExceptionHandler
import org.eclipse.jetty.server.HttpConnectionFactory
import org.eclipse.jetty.server.LowResourceMonitor
import org.eclipse.jetty.server.Server
import org.eclipse.jetty.server.ServerConnector
import org.eclipse.jetty.server.handler.StatisticsHandler
import org.eclipse.jetty.util.thread.QueuedThreadPool
import org.slf4j.LoggerFactory
import java.util.*

class HttpServer(private val port: Int = 80, private val managementPort: Int = -1, maxThreads: Int = 250) {
    private val app: Javalin
    private val logger = LoggerFactory.getLogger(javaClass.simpleName)
    private val routeRegister: RouteRegister
    private val statisticsHandler = StatisticsHandler()
    private val threadPool = QueuedThreadPool(maxThreads, 8, 60_000)
    private var managementThreadPool: QueuedThreadPool? = null
    val uid = UUID.randomUUID().toString()

    init {
        System.setProperty("org.slf4j.simpleLogger.showShortLogName", "true")
        app = Javalin.create { config ->
            config.showJavalinBanner = false
            config.requestLogger(::logRequest)
            config.enableCorsForAllOrigins()
            config.server { createJettyServer() }
        }
        routeRegister = RouteRegister(app)
        handleKnownExceptions()
    }

    private fun createJettyServer(): Server {
        threadPool.idleTimeout = 30000
        val server = Server(threadPool)
        server.addBean(LowResourceMonitor(server))
        server.insertHandler(statisticsHandler)
        val connector = ServerConnector(server)
        connector.port = port
        server.connectors = arrayOf(connector)
        if (managementPort > 0) {
            managementThreadPool = QueuedThreadPool(4, 2, 60_000)
            val managementConnector = ServerConnector(server, managementThreadPool, null, null, 1, 1, HttpConnectionFactory())
            managementConnector.port = managementPort
            server.connectors = arrayOf(connector, managementConnector)
        }
        return server
    }

    fun start() {
        logger.info("Threadpool configured with min: ${threadPool.minThreads} max: ${threadPool.maxThreads} idleTimeout: ${threadPool.idleTimeout}ms")
        app.start(port)
    }

    private fun registerController(controller: Controller) {
        controller.registerRoutesIn(routeRegister)
        logger.info(controller::class.qualifiedName + " registered")
        controller.getChildControllers().forEach { registerController(it) }
    }

    fun registerControllers(vararg controllers: Controller) {
        controllers.forEach { registerController(it) }
    }

    fun addInterceptor(interceptor: HttpRequestInterceptor) {
        app.before { interceptor.onRequest(it) }
    }

    private fun handleKnownExceptions() {
        app.exception(ParameterError::class.java, ::badRequestJsonErrorHandler)
        app.exception(JsonParseError::class.java, ::badRequestJsonErrorHandler)
        app.exception(Exception::class.java, ::internalServerJsonErrorHandler)
    }

    fun <T: Exception> registerException(clazz: Class<T>, handler: ExceptionHandler<T>) {
        app.exception(clazz, handler)
    }

    inline fun <reified T: Exception> registerException(handler: ExceptionHandler<T>) {
        registerException(T::class.java, handler)
    }

    fun <T: Exception> badRequestJsonErrorHandler(e: T, ctx: Context) {
        ctx.status(400)
        logger.info(e.message, e)
        ctx.jsonObj("type" to e.javaClass.simpleName, "message" to e.message)
    }

    fun <T: Exception> notAuthenticatedErrorHandler(e: T, ctx: Context) {
        ctx.status(401)
        logger.info(e.message, e)
        ctx.jsonObj("type" to e.javaClass.simpleName, "message" to e.message)
    }

    fun <T: Exception> forbiddenErrorHandler(e: T, ctx: Context) {
        ctx.status(403)
        logger.info(e.message, e)
        ctx.jsonObj("type" to e.javaClass.simpleName, "message" to e.message)
    }

    fun <T: Exception> notFoundErrorHandler(e: T, ctx: Context) {
        ctx.status(404)
        logger.info(e.message, e)
        ctx.jsonObj("type" to e.javaClass.simpleName, "message" to e.message)
    }

    private fun <T: Exception> internalServerJsonErrorHandler(e: T, ctx: Context) {
        logger.error("Uncaught exception ${e.javaClass.simpleName}: ${e.message}", e)
        ctx.status(500)
        ctx.jsonObj("message" to "Internal error")
    }

    private fun logRequest(ctx: Context, executionTimeMs: Float) {
        val sb = StringBuilder()
        sb.append(ctx.req.method)
        sb.append(" " + ctx.fullUrl())
        sb.append(" Response: " + ctx.res.status)
        sb.append(" - " + ctx.res.getHeader("content-type"))
        sb.append(" (" + executionTimeMs + "ms)")
        if (ctx.res.status >= 300 || ctx.res.status < 200) {
            sb.appendLine()
            sb.append("Request Body: " + ctx.body())
            sb.appendLine()
            sb.append("Response Body: " + ctx.resultString())
            logger.error(sb.toString())
            return
        }
        logger.info(sb.toString())
    }

    fun stop() {
        app.stop()
    }

    fun getStats(): ServerStats {
        return ServerStats(
            statsGatheringStartMs = statisticsHandler.statsOnMs,
            requests = RequestStats(
                totalRequests = statisticsHandler.requests,
                activeRequests = statisticsHandler.requestsActive,
                maxActiveRequests = statisticsHandler.requestsActiveMax,
                totalRequestsTimeMs = statisticsHandler.requestTimeTotal,
                meanRequestTimeMs = statisticsHandler.requestTimeMean,
                maxRequestTimeMs = statisticsHandler.requestTimeMax,
                standardDeviationRequestTimeMs = statisticsHandler.requestTimeStdDev,
            ),
            dispatches = DispatchStats(
                totalDispatched = statisticsHandler.dispatched,
                activeDispatched = statisticsHandler.dispatchedActive,
                maxActiveDispatched = statisticsHandler.dispatchedActiveMax,
                totalDispatchedTimeMs = statisticsHandler.dispatchedTimeTotal,
                meanDispatchedTimeMs = statisticsHandler.dispatchedTimeMean,
                maxDispatchedTimeMs = statisticsHandler.dispatchedTimeMax,
                standardDeviationDispatchedTimeMs = statisticsHandler.dispatchedTimeStdDev,
                totalRequestsSuspended = statisticsHandler.asyncRequests,
                totalRequestsExpired = statisticsHandler.expires,
                totalRequestsResumed = statisticsHandler.asyncDispatches,
            ),
            responses = ResponsesStats(
                responses1xx = statisticsHandler.responses1xx,
                responses2xx = statisticsHandler.responses2xx,
                responses3xx = statisticsHandler.responses3xx,
                responses4xx = statisticsHandler.responses4xx,
                responses5xx = statisticsHandler.responses5xx,
                bytesSentTotal = statisticsHandler.responsesBytesTotal,
            ),
            threads = threadsStats(threadPool),
            managementThreads = if (managementThreadPool != null) threadsStats(managementThreadPool!!) else null
        )
    }

    private fun threadsStats(threadPool: QueuedThreadPool) = ThreadsStats(
        state = threadPool.state,
        isLowOnThreads = threadPool.isLowOnThreads,
        minThreads = threadPool.minThreads,
        threads = threadPool.threads,
        maxThreads = threadPool.maxThreads,
        idleThreads = threadPool.idleThreads,
        reservedThreads = threadPool.reservedThreads,
        busyThreads = threadPool.busyThreads,
        queueSize = threadPool.queueSize
    )
}
