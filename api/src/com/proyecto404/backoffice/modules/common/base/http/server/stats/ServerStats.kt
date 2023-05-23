package com.proyecto404.backoffice.modules.common.base.http.server.stats

class ServerStats(
    val statsGatheringStartMs: Long,
    val requests: RequestStats,
    val dispatches: DispatchStats,
    val responses: ResponsesStats,
    val threads: ThreadsStats,
    val managementThreads: ThreadsStats?,
)

class RequestStats(
    val totalRequests: Int,
    val activeRequests: Int,
    val maxActiveRequests: Int,
    val totalRequestsTimeMs: Long,
    val meanRequestTimeMs: Double,
    val maxRequestTimeMs: Long,
    val standardDeviationRequestTimeMs: Double,
)

class DispatchStats(
    val totalDispatched: Int,
    val activeDispatched: Int,
    val maxActiveDispatched: Int,
    val totalDispatchedTimeMs: Long,
    val meanDispatchedTimeMs: Double,
    val maxDispatchedTimeMs: Long,
    val standardDeviationDispatchedTimeMs: Double,
    val totalRequestsSuspended: Int,
    val totalRequestsExpired: Int,
    val totalRequestsResumed: Int
)

class ResponsesStats(
    val responses1xx: Int,
    val responses2xx: Int,
    val responses3xx: Int,
    val responses4xx: Int,
    val responses5xx: Int,
    val bytesSentTotal: Long
)

class ThreadsStats(
    val state: String,
    val isLowOnThreads: Boolean,
    val minThreads: Int,
    val threads: Int,
    val maxThreads: Int,
    val idleThreads: Int,
    val reservedThreads: Int,
    val busyThreads: Int,
    val queueSize: Int
)
