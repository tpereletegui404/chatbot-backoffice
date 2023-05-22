import { NextApiRequest, NextApiResponse } from 'next'
import httpProxy from 'http-proxy'
import Cookies from 'cookies'
import { proxyRequest } from '../../ui/services/httpProxy/proxyRequest'

const SESSION_COOKIE = 'session_token'

const proxyServer = httpProxy.createProxyServer({
    target: process.env.API_BASE_URL,
    changeOrigin: true,
    selfHandleResponse: true,
})

export default async function handler(req: NextApiRequest, res: NextApiResponse) {
    const cookies = new Cookies(req, res)
    removeApiPrefix(req)
    setAuthHeader(req, cookies.get(SESSION_COOKIE))
    removeRequestCookies(req)

    await proxyRequest(proxyServer, req, res, (buffer) => {
        if (!req.url?.startsWith('/login')) return
        const jsonBody = bufferToJson(buffer)
        if (!jsonBody.isSuccess) return jsonToBuffer(jsonBody)
        setSessionCookie(cookies, jsonBody.sessionToken)
        delete jsonBody.sessionToken
        return jsonToBuffer(jsonBody)
    })
}

const removeApiPrefix = (request) => request.url = request.url.replace(/^\/api/, '')

const removeRequestCookies = (request) => request.headers.cookie = ''

function setAuthHeader(request, sessionToken) {
    if (!sessionToken) return
    request.headers.authorization = 'Bearer ' + sessionToken
}

function setSessionCookie(cookies, sessionToken) {
    cookies.set(SESSION_COOKIE, sessionToken, { httpOnly: true, sameSite: 'Strict' })
}

const bufferToJson = (buffer) => JSON.parse(buffer.toString())

const jsonToBuffer = (json) =>  Buffer.from(JSON.stringify(json), 'utf8')

export const config = {
    api: { bodyParser: false },
}
