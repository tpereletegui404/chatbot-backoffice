import { SimpleStorage } from '@/core/common/infrastructure/simpleStorage/SimpleStorage'
import { Nullable } from '@/core/common/base/lang/Nullable'

export class InMemorySimpleStorage implements SimpleStorage {
    private data: Map<string, string> = new Map()

    async get(key: string): Promise<Nullable<string>> {
        return this.data.get(key) || null
    }

    async set(key: string, value: string) {
        this.data.set(key, value)
    }
}
