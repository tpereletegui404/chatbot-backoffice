import { LocalDate } from '@/core/common/base/time/LocalDate'

it('can access date parts', () => {
    const date = LocalDate.of(2020, 6, 12)

    expect(date.year).toEqual(2020)
    expect(date.month).toEqual(6)
    expect(date.day).toEqual(12)
})

it('equals', () => {
    expect(LocalDate.of(2020, 6, 12)).toEqual(LocalDate.of(2020, 6, 12))
    expect(LocalDate.of(2020, 6, 12)).not.toEqual(LocalDate.of(2021, 7, 12))
})

it('addDays', () => {
    expect(LocalDate.of(2020, 6, 12).addDays(1)).toEqual(LocalDate.of(2020, 6, 13))
    expect(LocalDate.of(2020, 12, 31).addDays(1)).toEqual(LocalDate.of(2021, 1, 1))
    expect(LocalDate.of(2020, 6, 12).addDays(5)).toEqual(LocalDate.of(2020, 6, 17))
    expect(LocalDate.of(2020, 6, 12).addDays(-1)).toEqual(LocalDate.of(2020, 6, 11))
})

it('minusDays', () => {
    expect(LocalDate.of(2020, 6, 12).minusDays(1)).toEqual(LocalDate.of(2020, 6, 11))
    expect(LocalDate.of(2021, 1, 1).minusDays(1)).toEqual(LocalDate.of(2020, 12, 31))
    expect(LocalDate.of(2020, 6, 12).minusDays(5)).toEqual(LocalDate.of(2020, 6, 7))
})

it('addMonths', () => {
    expect(LocalDate.of(2020, 6, 12).addMonths(1)).toEqual(LocalDate.of(2020, 7, 12))
    expect(LocalDate.of(2020, 12, 15).addMonths(1)).toEqual(LocalDate.of(2021, 1, 15))
    expect(LocalDate.of(2020, 12, 15).addMonths(-1)).toEqual(LocalDate.of(2020, 11, 15))
    expect(LocalDate.of(2021, 1, 30).addMonths(1)).toEqual(LocalDate.of(2021, 3, 2))
})

it('minusMonths', () => {
    expect(LocalDate.of(2020, 6, 12).minusMonths(1)).toEqual(LocalDate.of(2020, 5, 12))
    expect(LocalDate.of(2021, 1, 15).minusMonths(1)).toEqual(LocalDate.of(2020, 12, 15))
    expect(LocalDate.of(2021, 3, 2).minusMonths(1)).toEqual(LocalDate.of(2021, 2, 2))
})

it('toISO8601String', () => {
    expect(LocalDate.of(2020, 6, 12).toISO8601String()).toEqual('2020-06-12')
    expect(LocalDate.of(2020, 6, 1).toISO8601String()).toEqual('2020-06-01')
})

it('fromISO8601String', () => {
    expect(LocalDate.fromISO8601String('2020-06-12')).toEqual(LocalDate.of(2020, 6, 12))
    expect(LocalDate.fromISO8601String('2020-06-1')).toEqual(LocalDate.of(2020, 6, 1))
})

it('from standard date', () => {
    const date = 'Thu Mar 03 2022 00:00:00 GMT-0300 (hora estándar de Argentina)'
    const date2 = 'Thu Apr 03 2022 00:00:00 GMT-0300 (hora estándar de Argentina)'

    expect(LocalDate.fromStandardDate(date)).toEqual(LocalDate.of(2022, 3, 3))
    expect(LocalDate.fromStandardDate(date2)).toEqual(LocalDate.of(2022, 4, 3))
})

it('fromDDMMYYYYString', () => {
    expect(LocalDate.fromDDMMYYYYString('12/6/2020')).toEqual(LocalDate.of(2020, 6, 12))
    expect(LocalDate.fromDDMMYYYYString('1/06/2020')).toEqual(LocalDate.of(2020, 6, 1))
})

it('fromDate', () => {
    expect(LocalDate.fromDate(new Date(2020, 6, 1))).toEqual(LocalDate.of(2020, 7, 1))
})

it('toDate', () => {
    const date = LocalDate.of(2020, 7, 1).toDate()

    expect(date.getFullYear()).toEqual(2020)
    expect(date.getMonth()).toEqual(6)
    expect(date.getDate()).toEqual(1)
    expect(date.getHours()).toEqual(0)
    expect(date.getMinutes()).toEqual(0)
    expect(date.getSeconds()).toEqual(0)
    expect(date.getMilliseconds()).toEqual(0)
})
