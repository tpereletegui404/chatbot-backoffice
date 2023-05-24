import { RequestHandler } from 'asimov-cqbus/dist/requests/handlers/RequestHandler'
import { HttpClient } from '../../../common/infrastructure/http/HttpClient'
import { UpdateContext } from '../../app/UpdateContext'

export class HttpCreateContextHandler implements RequestHandler<UpdateContext> {
    public constructor(private httpClient: HttpClient) {}

    async handle(request: UpdateContext) {
        await this.httpClient.post('/configuration/context', { context: request.context })
    }
}
