export interface TimeZone {
    localOffset(): number
}

export class LocalTimeZone implements TimeZone {
    localOffset(): number {
        const inversedOffset = ((new Date()).getTimezoneOffset() / 60)
        if (inversedOffset === 0) return 0 // Javascript problem
        return -inversedOffset
    }
}
