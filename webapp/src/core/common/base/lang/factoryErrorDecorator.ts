import { methodProxy } from './methodProxy'
import { unhandledPromiseProxy } from './unhandledPromiseProxy'

export type ErrorHandler = (e: Error) => void

export function factoryErrorDecorator<T>(factory: T, onUnhandledError: ErrorHandler): T {
    return methodProxy(factory, (method) => {
        return new Proxy(method, {
            apply: (target, thisArg, argumentsList) => {
                const result = target.apply(thisArg, argumentsList)
                return unhandledPromiseProxy(result, onUnhandledError)
            },
        })
    })
}
