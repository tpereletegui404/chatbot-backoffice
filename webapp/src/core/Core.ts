import { ExecutionContext } from 'asimov-cqbus/dist/ExecutionContext'
import { CQBus, RequestResult } from 'asimov-cqbus/dist/CQBus'
import { Middleware } from 'asimov-cqbus/dist/Middleware'
import { Request } from 'asimov-cqbus/dist/requests/Request'
import { HttpClient } from './common/infrastructure/http/HttpClient'
import { HttpInterceptor } from './common/infrastructure/http/HttpInterceptor'
import { JsonDateSerializer } from './common/base/time/JsonDateSerializer'
import { SecurityModule } from './security/SecurityModule'

export class Core {
    private readonly services: Services

    constructor(private config: CoreConfig) {
        this.services = { config }

        new SecurityModule(this.config, this.services)
    }

    async execute<T extends Request<any>>(request: T, context = ExecutionContext.empty()): Promise<RequestResult<T>> {
        return await this.config.cqBus.execute(request, context)
    }

    registerMiddleware(middleware: Middleware) {
        this.config.cqBus.registerMiddleware(middleware)
    }

    registerInterceptor(interceptor: HttpInterceptor) {
        this.config.httpClient.addInterceptor(interceptor)
    }
}

export interface Services {
    config: CoreConfig,
}

export interface CoreConfig {
    cqBus: CQBus,
    httpClient: HttpClient,
    jsonDateSerializer: JsonDateSerializer,
}
