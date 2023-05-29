import { RequestHandler } from 'asimov-cqbus/dist/requests/handlers/RequestHandler'
import { HttpClient } from '../../../common/infrastructure/http/HttpClient'
import { UpdateContext } from '../../app/UpdateContext'
import { UpdateConfiguration } from '../../app/UpdateConfiguration'

export class HttpUpdateConfigurationHandler implements RequestHandler<UpdateContext> {
    constructor(public http: HttpClient) {}

    async handle(request: UpdateConfiguration) {
        const json = this.toJson(request)
        await this.http.put(`/configurations`, json)
    }

    private toJson(request: UpdateConfiguration) {
        return {
            apiKey: request.apiKey ?? null,
            context: request.context ?? null,
            maxTokens: request.maxTokens,
            temperature: request.temperature,
            topP: request.topP,
            frequencyPenalty: request.frequencyPenalty,
            parameterPresencePenalty: request.parameterPresencePenalty,
        }
    }
}
