import { Login } from './app/Login'
import { HttpLoginHandler } from './infrastructure/http/HttpLoginHandler'
import { CoreConfig, Services } from '../Core'

export class SecurityModule {
    constructor(private config: CoreConfig, private services: Services) {
        const cqBus = config.cqBus
        cqBus.registerHandler(Login, () => new HttpLoginHandler(services.config.httpClient))
    }
}
