export function methodProxy(obj, getMethod) {
    const handler = {
        get: (target, prop, receiver) => {
            if (typeof target[prop] === 'function') return getMethod(target[prop])
            return target[prop]
        },
    }
    return new Proxy(obj, handler)
}
