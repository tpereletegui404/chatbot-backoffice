import { CoreConfig, Services } from '../Core'
import { UpdateContext } from './app/GetContext'
import { HttpCreateContextHandler } from './infrastructure/http/HttpCreateContextHandler'

export class ChatbotSettingsModule {
    constructor(private config: CoreConfig, private services: Services) {
        const cqBus = config.cqBus
        cqBus.registerHandler(UpdateContext, () => new HttpCreateContextHandler(services.config.httpClient))
    }
}
