import { HttpInterceptor } from '@/core/common/infrastructure/http/HttpInterceptor'
import { HttpRequest } from '@/core/common/infrastructure/http/HttpRequest'
import { HttpResponse } from '@/core/common/infrastructure/http/HttpResponse'
import { SimpleStorage } from '@/core/common/infrastructure/simpleStorage/SimpleStorage'

const SESSION_KEY = 'SESSION_TOKEN'

export class AuthHttpInterceptor implements HttpInterceptor {
    constructor(private storage: SimpleStorage) {}

    async onRequest(request: HttpRequest) {
        const sessionToken = await this.storage.get(SESSION_KEY)
        if (!sessionToken) return
        request.headers['Authorization'] = `Bearer ${sessionToken}`
    }

    async onResponse(response: HttpResponse<any>) {
        if (!response.relativeUrl.startsWith('/login')) return
        const sessionToken = response.body.success?.sessionToken ?? response.body.sessionToken
        if (!sessionToken) return
        await this.storage.set(SESSION_KEY, sessionToken)
    }
}
