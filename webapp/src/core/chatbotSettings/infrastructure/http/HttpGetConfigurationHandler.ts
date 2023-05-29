import { RequestHandler } from 'asimov-cqbus/dist/requests/handlers/RequestHandler'
import { HttpClient } from '../../../common/infrastructure/http/HttpClient'
import { Configuration } from '../../model/Configuration'
import { GetConfiguration } from '../../app/GetConfiguration'

export class HttpGetConfigurationHandler implements RequestHandler<GetConfiguration, Configuration> {
    public constructor(private httpClient: HttpClient) {}

    async handle(request: GetConfiguration): Promise<Configuration> {
        const response = await this.httpClient.get('/configurations')
        return Configuration.fromJson(response.body)
    }
}
