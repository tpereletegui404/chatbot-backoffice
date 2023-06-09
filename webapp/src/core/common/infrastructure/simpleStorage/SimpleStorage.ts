import { Nullable } from '../../base/lang/Nullable'

export interface SimpleStorage {
    get(key: string): Promise<Nullable<string>>
    set(key: string, value: string): Promise<void>
}
