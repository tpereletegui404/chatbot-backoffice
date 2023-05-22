import { LocalDate } from '@/core/common/base/time/LocalDate'

export class YearMonth {
    private readonly _year: number
    private readonly _month: number

    private constructor(year: number, month: number) {
        this._year = year
        this._month = month
    }

    get month(): number {
        return this._month
    }

    get year(): number {
        return this._year
    }

    equals(other: YearMonth | null): boolean {
        if (other === null) return false
        return this.year === other.year && this.month === other.month
    }

    toMMYYYYString(): string {
        return `${this.month}/${this.year}`
    }

    toISO8610String(): string {
        const monthString = this.month.toString(10).padZeros(2)
        return `${this.year}-${monthString}`
    }

    static of(year: number, month: number) {
        return new YearMonth(year, month)
    }

    static fromLocalDate(date: LocalDate) {
        return new YearMonth(date.year, date.month)
    }

    static fromISO8601String(value: string): YearMonth {
        const parts = value.split('-')
        return YearMonth.of(parseInt(parts[0], 10), parseInt(parts[1], 10))
    }

    static fromMMYYYYString(value: string): YearMonth {
        const parts = value.split('/')
        return YearMonth.of(parseInt(parts[1], 10), parseInt(parts[0], 10))
    }
}
