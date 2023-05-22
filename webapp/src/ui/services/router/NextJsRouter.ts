import { Router } from './Router'
import singletonRouter from 'next/router'

export class NextJsRouter implements Router {
    get query() {
        return singletonRouter.router.query
    }

    get path() {
        return singletonRouter.router.pathname
    }

    navigate(route: string) {
        singletonRouter.router.push(route)
    }

    replace(route: string) {
        singletonRouter.router.replace(route)
    }
}
