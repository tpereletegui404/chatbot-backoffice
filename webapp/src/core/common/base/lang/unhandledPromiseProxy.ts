import { methodProxy } from './methodProxy'

export function unhandledPromiseProxy(obj, handleError) {
    const getMethod = (method) => {
        return new Proxy(method, {
            apply: (target, thisArg, argumentsList) => {
                const result = target.apply(obj, argumentsList)
                if (result instanceof Promise) return result.catch(e => handleError(e))
                return result
            },
        })
    }
    return methodProxy(obj, getMethod)
}
