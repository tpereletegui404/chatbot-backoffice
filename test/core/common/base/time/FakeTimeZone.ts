import { TimeZone } from '@/core/common/base/time/TimeZone'

export class FakeTimeZone implements TimeZone {
    private readonly _localOffSet: number

    constructor(private offset: number) {
        this._localOffSet = offset
    }

    localOffset() {
        return this._localOffSet
    }
}
