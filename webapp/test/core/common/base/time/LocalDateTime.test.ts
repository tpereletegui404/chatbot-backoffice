import { LocalDate } from '../../../../../src/core/common/base/time/LocalDate'
import { LocalDateTime } from '../../../../../src/core/common/base/time/LocalDateTime'
import { FakeTimeZone } from './FakeTimeZone'

it('can access date parts', () => {
    const date = LocalDateTime.of(2020, 6, 12, 22, 10, 33, 67)

    expect(date.year).toEqual(2020)
    expect(date.month).toEqual(6)
    expect(date.day).toEqual(12)
    expect(date.hours).toEqual(22)
    expect(date.minutes).toEqual(10)
    expect(date.seconds).toEqual(33)
    expect(date.milliseconds).toEqual(67)
})

it('equals', () => {
    expect(LocalDateTime.of(2020, 6, 12, 22, 10, 33, 67)).toEqual(LocalDateTime.of(2020, 6, 12, 22, 10, 33, 67))
    expect(LocalDateTime.of(2020, 6, 12, 22, 10, 33, 67)).not.toEqual(LocalDateTime.of(2020, 6, 12, 23, 10, 33, 67))
})

it('toDate', () => {
    expect(LocalDateTime.of(2020, 6, 12, 22, 10, 33, 67).toDate()).toEqual(LocalDate.of(2020, 6, 12))
})

it('fromISO8601DateUTC', () => {
    expect(LocalDateTime.fromISO8601DateUTC('2020-09-12T22:10:23Z', new FakeTimeZone(-3)))
        .toEqual(LocalDateTime.of(2020, 9, 12, 19, 10, 23))
})

it('toISO8601DateUTC', () => {
    const date = LocalDateTime.of(2020, 9, 12, 19, 10, 23)
    expect(date.toISO8601DateUTC(new FakeTimeZone(-3))).toEqual('2020-09-12T22:10:23Z')
})

it('fromDate', () => {
    expect(LocalDateTime.fromDate(new Date(2020, 6, 1, 22, 33, 22, 67))).toEqual(LocalDateTime.of(2020, 7, 1, 22, 33, 22, 67))
})
