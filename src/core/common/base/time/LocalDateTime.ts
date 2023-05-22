import { LocalDate } from './LocalDate'
import { TimeZone } from './TimeZone'

export class LocalDateTime {
    private readonly _day: number
    private readonly _month: number
    private readonly _year: number
    private readonly _hours: number
    private readonly _minutes: number
    private readonly _seconds: number
    private readonly _milliseconds: number

    constructor(year: number, month: number, day: number, hours: number, minutes: number, seconds: number, milliseconds: number) {
        this._year = year
        this._month = month
        this._day = day
        this._hours = hours
        this._minutes = minutes
        this._seconds = seconds
        this._milliseconds = milliseconds
    }

    get day(): number {
        return this._day
    }

    get month(): number {
        return this._month
    }

    get year(): number {
        return this._year
    }

    get hours(): number {
        return this._hours
    }

    get minutes(): number {
        return this._minutes
    }

    get seconds(): number {
        return this._seconds
    }

    get milliseconds(): number {
        return this._milliseconds
    }

    equals(other: LocalDateTime) {
        return this.day === other.day &&
            this.month === other.month &&
            this.year === other.year &&
            this.hours === other.hours &&
            this.minutes === other.minutes &&
            this.seconds === other.seconds &&
            this.milliseconds === other.milliseconds
    }

    toDate(): LocalDate {
        return LocalDate.of(this.year, this.month, this.day)
    }

    static of(year: number, month: number, day: number, hours = 0, minutes = 0, seconds = 0, milliseconds = 0) {
        return new LocalDateTime(year, month, day, hours, minutes, seconds, milliseconds)
    }

    static fromDate(date: Date) {
        return new LocalDateTime(date.getFullYear(), date.getMonth() + 1, date.getDate(), date.getHours(), date.getMinutes(), date.getSeconds(), date.getMilliseconds())
    }

    static now() {
        return LocalDateTime.fromDate(new Date())
    }

    toISO8601DateUTC(timeZone: TimeZone): string {
        const date = new Date(this.year, this.month - 1, this.day, this.hours, this.minutes, this.seconds, this.milliseconds)
        date.setHours(date.getHours() - timeZone.localOffset())
        const dayStr = date.getDate().toString(10).padZeros(2)
        const monthStr = (date.getMonth() + 1).toString(10).padZeros(2)
        const hoursStr = date.getHours().toString(10).padZeros(2)
        const minutesStr = date.getMinutes().toString(10).padZeros(2)
        const secondsStr = date.getSeconds().toString(10).padZeros(2)
        return `${this.year}-${monthStr}-${dayStr}T${hoursStr}:${minutesStr}:${secondsStr}Z`
    }

    static fromISO8601DateUTC(iso8601Date: string, timeZone: TimeZone): LocalDateTime {
        const date = new Date(iso8601Date)
        date.setUTCHours(date.getUTCHours() + timeZone.localOffset())
        return LocalDateTime.of(
            date.getUTCFullYear(),
            date.getUTCMonth() + 1,
            date.getUTCDate(),
            date.getUTCHours(),
            date.getUTCMinutes(),
            date.getUTCSeconds(),
            date.getUTCMilliseconds()
        )
    }
}
