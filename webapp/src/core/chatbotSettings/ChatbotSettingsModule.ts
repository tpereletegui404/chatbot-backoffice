import { CoreConfig, Services } from '../Core'
import { UpdateContext } from './app/UpdateContext'
import { HttpUpdateContextHandler } from './infrastructure/http/HttpUpdateContextHandler'
import { GetConfiguration } from './app/GetConfiguration'
import { HttpGetConfigurationHandler } from './infrastructure/http/HttpGetConfigurationHandler'
import { UpdateConfiguration } from './app/UpdateConfiguration'
import { HttpUpdateConfigurationHandler } from './infrastructure/http/HttpUpdateConfigurationHandler'

export class ChatbotSettingsModule {
    constructor(private config: CoreConfig, private services: Services) {
        const cqBus = config.cqBus
        cqBus.registerHandler(UpdateContext, () => new HttpUpdateContextHandler(services.config.httpClient))
        cqBus.registerHandler(GetConfiguration, () => new HttpGetConfigurationHandler(services.config.httpClient))
        cqBus.registerHandler(UpdateConfiguration, () => new HttpUpdateConfigurationHandler(services.config.httpClient))
    }
}
