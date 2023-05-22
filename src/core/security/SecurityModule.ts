import { Login } from '@/core/security/app/Login'
import { HttpLoginHandler } from '@/core/security/infrastructure/http/HttpLoginHandler'
import { CoreConfig, Services } from '@/core/Core'

export class SecurityModule {
    constructor(private config: CoreConfig, private services: Services) {
        const cqBus = config.cqBus
        cqBus.registerHandler(Login, () => new HttpLoginHandler(services.config.httpClient))
    }
}
