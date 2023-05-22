import { User } from '@/core/security/model/User'
import { Login, LoginResponse } from '@/core/security/app/Login'
import { RequestHandler } from 'asimov-cqbus/dist/requests/handlers/RequestHandler'
import { Identity } from 'asimov-cqbus/dist/identity/Identity'
import { HttpClient } from '@/core/common/infrastructure/http/HttpClient'

export class HttpLoginHandler implements RequestHandler<Login, LoginResponse> {
    public constructor(private httpClient: HttpClient) {}

    async handle(request: Login, identity: Identity): Promise<LoginResponse> {
        const response = await this.httpClient.post('/login', { username: request.username, password: request.password })
        if (!response.body.isSuccessful) return { isSuccessful: false, user: null }
        return { user: User.fromJson(response.body.user), isSuccessful: response.body.isSuccessful }
    }
}
