import { User } from '../../model/User'
import { Login, LoginResponse } from '../../app/Login'
import { RequestHandler } from 'asimov-cqbus/dist/requests/handlers/RequestHandler'
import { Identity } from 'asimov-cqbus/dist/identity/Identity'
import { HttpClient } from '../../../common/infrastructure/http/HttpClient'
import {CreateContext} from "../../app/GetContext"

export class HttpCreateContextHandler implements RequestHandler<CreateContext> {
    public constructor(private httpClient: HttpClient) {}

    async handle(request: CreateContext) {
        await this.httpClient.post('/context', { context: request.context })
    }
}
