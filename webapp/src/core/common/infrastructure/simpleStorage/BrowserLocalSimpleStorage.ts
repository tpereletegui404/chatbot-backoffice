import { SimpleStorage } from './SimpleStorage'
import { Nullable } from '../../base/lang/Nullable'

export class BrowserLocalSimpleStorage implements SimpleStorage {
    async get(key: string): Promise<Nullable<string>> {
        return localStorage.getItem(key)
    }

    async set(key: string, value: string) {
        localStorage.setItem(key, value)
    }
}
