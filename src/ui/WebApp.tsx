import { MainLayout } from '@/ui/layouts/MainLayout'
import { SessionStorage } from '@/ui/services/security/SessionStorage'
import { Core } from '@/core/Core'
import { Router } from '@/ui/services/router/Router'
import { CQBus } from 'asimov-cqbus/dist/CQBus'
import { NextJsRouter } from '@/ui/services/router/NextJsRouter'
import { AxiosHttpClient } from '@/core/common/infrastructure/http/axiosClient/AxiosHttpClient'
import { AuthHttpInterceptor } from '@/core/common/infrastructure/http/interceptors/AuthHttpInterceptor'
import { NetworkError } from '@/core/common/infrastructure/http/errors/NetworkError'
import { AuthenticationError } from '@/core/common/infrastructure/http/errors/AuthenticationError'
import { JsonDateSerializer } from '@/core/common/base/time/JsonDateSerializer'
import { LocalTimeZone } from '@/core/common/base/time/TimeZone'
import React from 'react'
import { AppServicesProvider } from '@/ui/components/context/AppServicesContext'
import { unhandledPromiseProxy } from '@/core/common/base/lang/unhandledPromiseProxy'
import { BrowserLocalSimpleStorage } from '@/core/common/infrastructure/simpleStorage/BrowserLocalSimpleStorage'

export class WebApp {
    private readonly services: WebAppServices

    constructor(private config: WebAppConfig) {
        this.services = {
            ...config,
            unhandledErrorDecorator: (obj) => unhandledPromiseProxy(obj, this.onUnhandledError)
        }
        this.registerHttpInterceptors()
    }

    private onUnhandledError = async (e: Error) => {
        switch (true) {
            case e instanceof AuthenticationError:
                await this.config.sessionStorage.do(session => session.logout())
                this.config.router.navigate('/login')
                break
            case e instanceof NetworkError:
                break
            default: {
                console.error(e)
            }
        }
    }

    private registerHttpInterceptors() {
        this.config.core.registerInterceptor(new AuthHttpInterceptor(new BrowserLocalSimpleStorage()))
    }

    render = (PageComponent, pageProps = {}) => (
        <AppServicesProvider services={this.services}>
            <MainLayout>
                <PageComponent {...pageProps} />
            </MainLayout>
        </AppServicesProvider>
    )
}

export interface WebAppConfig {
    sessionStorage: SessionStorage,
    core: Core,
    router: Router,
}

export interface WebAppServices extends WebAppConfig {
    unhandledErrorDecorator<T>(obj: T): T
}

export const defaultWebAppConfig = (): WebAppConfig => {
    return {
        sessionStorage: new SessionStorage(new BrowserLocalSimpleStorage()),
        core: new Core({
            cqBus: new CQBus(),
            httpClient: new AxiosHttpClient(process.env.NEXT_PUBLIC_API_BASE_URL),
            jsonDateSerializer: new JsonDateSerializer(new LocalTimeZone()),
        }),
        router: new NextJsRouter(),
    }
}
