import { User } from '../../model/User'
import { RequestHandler } from 'asimov-cqbus/dist/requests/handlers/RequestHandler'
import { Identity } from 'asimov-cqbus/dist/identity/Identity'
import { HttpClient } from '../../../common/infrastructure/http/HttpClient'
import {UpdateContext} from "../../app/GetContext"

export class HttpCreateContextHandler implements RequestHandler<UpdateContext> {
    public constructor(private httpClient: HttpClient) {}

    async handle(request: UpdateContext) {
        await this.httpClient.post('/context', { context: request.context })
    }
}
