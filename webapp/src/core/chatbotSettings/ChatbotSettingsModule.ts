import { CoreConfig, Services } from '../Core'
import { UpdateContext } from './app/UpdateContext'
import { HttpCreateContextHandler } from './infrastructure/http/HttpCreateContextHandler'
import { GetConfiguration } from './app/GetConfiguration'
import { HttpGetConfigurationHandler } from './infrastructure/http/HttpGetConfigurationHandler'

export class ChatbotSettingsModule {
    constructor(private config: CoreConfig, private services: Services) {
        const cqBus = config.cqBus
        cqBus.registerHandler(UpdateContext, () => new HttpCreateContextHandler(services.config.httpClient))
        cqBus.registerHandler(GetConfiguration, () => new HttpGetConfigurationHandler(services.config.httpClient))
    }
}
