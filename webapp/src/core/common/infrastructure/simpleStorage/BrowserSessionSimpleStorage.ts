import { SimpleStorage } from './SimpleStorage'
import { Nullable } from '../../base/lang/Nullable'

export class BrowserSessionSimpleStorage implements SimpleStorage {
    async get(key: string): Promise<Nullable<string>> {
        return sessionStorage.getItem(key)
    }

    async set(key: string, value: string) {
        sessionStorage.setItem(key, value)
    }
}
