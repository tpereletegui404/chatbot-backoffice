export async function proxyRequest(proxyServer, req, res, responseInterceptor) {
    return new Promise<void>((resolve, reject) => {
        proxyServer.once("error", reject)
        proxyServer.once("proxyRes", (proxyRes, request, response) => {
            let buffer = Buffer.from('', 'utf8')
            proxyRes.on("data", (chunk) => buffer = Buffer.concat([buffer, chunk]))
            proxyRes.on("error", reject)
            proxyRes.on("end", () => {
                copyStatusAndHeaders(proxyRes, response)
                let newBuffer = responseInterceptor(buffer, proxyRes)
                if (typeof newBuffer == 'undefined') {
                    newBuffer = buffer
                }
                response.setHeader('content-length', Buffer.byteLength(newBuffer, 'utf8'))
                response.end(newBuffer)
                resolve()
            })
        })

        proxyServer.web(req, res)
    })
}

function copyStatusAndHeaders(originalResponse, response) {
    response.statusCode = originalResponse.statusCode
    response.statusMessage = originalResponse.statusMessage
    Object.keys(originalResponse.headers).forEach((key) => {
        response.setHeader(key, originalResponse.headers[key])
    })
}
