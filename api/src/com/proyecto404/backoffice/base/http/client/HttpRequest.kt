package com.proyecto404.backoffice.base.http.client

class HttpRequest(val url: String) {
    var body: String? = null
    val headers = mutableMapOf<String, String>()

    fun setHeader(header: String, value: String) {
        headers[header] = value
    }

    override fun toString() = "HttpRequest(url=$url, body=$body, headers=$headers)"

    override fun equals(other: Any?) =
        other is HttpRequest &&
        other.url == url &&
        other.body == body &&
        other.headers == headers

    override fun hashCode(): Int {
        var result = url.hashCode()
        result = 31 * result + (body?.hashCode() ?: 0)
        result = 31 * result + headers.hashCode()
        return result
    }
}
