import { TimeZone } from './TimeZone'
import { LocalDateTime } from './LocalDateTime'

export class JsonDateSerializer {
    constructor(public timeZone: TimeZone) {}

    fromISO8601(date: string): LocalDateTime {
        return LocalDateTime.fromISO8601DateUTC(date, this.timeZone)
    }

    toISO8601(date: LocalDateTime): string {
        return date.toISO8601DateUTC(this.timeZone)
    }
}
