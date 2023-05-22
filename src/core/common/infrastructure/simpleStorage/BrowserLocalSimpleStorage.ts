import { SimpleStorage } from '@/core/common/infrastructure/simpleStorage/SimpleStorage'
import { Nullable } from '@/core/common/base/lang/Nullable'

export class BrowserLocalSimpleStorage implements SimpleStorage {
    async get(key: string): Promise<Nullable<string>> {
        return localStorage.getItem(key)
    }

    async set(key: string, value: string) {
        localStorage.setItem(key, value)
    }
}
