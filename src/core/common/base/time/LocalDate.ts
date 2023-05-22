export class LocalDate {
    private readonly _day: number
    private readonly _month: number
    private readonly _year: number

    private constructor(year: number, month: number, day: number) {
        this._year = year
        this._month = month
        this._day = day
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

    toISO8601String(): string {
        const dayStr = this.day.toString(10).padZeros(2)
        const monthStr = this.month.toString(10).padZeros(2)
        return `${this.year}-${monthStr}-${dayStr}`
    }

    toDDMMYYYYString(): string {
        const dayStr = this.day.toString(10).padZeros(2)
        const monthStr = this.month.toString(10).padZeros(2)
        return `${dayStr}/${monthStr}/${this.year}`
    }

    toDate(): Date {
        return new Date(this.year, this.month - 1, this.day, 0, 0, 0, 0)
    }

    equals(other: LocalDate | null): boolean {
        if (other === null) return false
        return this.day === other.day && this.month === other.month && this.year === other.year
    }

    addDays(days: number) {
        const date = this.toDate()
        date.setDate(date.getDate() + days)
        return LocalDate.fromDate(date)
    }

    minusDays(days: number) {
        return this.addDays(-days)
    }

    addMonths(months: number) {
        const date = this.toDate()
        date.setMonth(date.getMonth() + months)
        return LocalDate.fromDate(date)
    }

    minusMonths(months: number) {
        return this.addMonths(-months)
    }

    isLaterThan(date: LocalDate) {
        return this.toDate() > date.toDate()
    }

    static of(year: number, month: number, day: number) {
        return new LocalDate(year, month, day)
    }

    static fromISO8601String(value: string) {
        const parts = value.split('-')
        return LocalDate.of(parseInt(parts[0], 10), parseInt(parts[1], 10), parseInt(parts[2], 10))
    }

    static fromDDMMYYYYString(value: string) {
        const parts = value.split('/')
        return LocalDate.of(parseInt(parts[2], 10), parseInt(parts[1], 10), parseInt(parts[0], 10))
    }

    static fromDate(date: Date) {
        return new LocalDate(date.getFullYear(), date.getMonth() + 1, date.getDate())
    }

    static today() {
        return this.fromDate(new Date())
    }

    static fromStandardDate(date: string) {
        const parts = date.toString().split(' ')
        return LocalDate.of(parseInt(parts[3], 10), this.getMonthFromName(parts[1]), parseInt(parts[2], 10))
    }

    private static getMonthFromName(name: string): number {
        const months = { 'Jan': 1, 'Feb': 2, 'Mar': 3, 'Apr': 4, 'May': 5, 'Jun': 6, 'Jul': 7, 'Aug': 8, 'Sep': 9, 'Oct': 10, 'Nov': 11, 'Dec': 12 }
        return months[name]
    }
}
