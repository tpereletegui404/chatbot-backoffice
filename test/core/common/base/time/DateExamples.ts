import { LocalDateTime } from '@/core/common/base/time/LocalDateTime'
import { LocalDate } from '@/core/common/base/time/LocalDate'

export const now = LocalDateTime.of(2020, 7, 20, 10, 20, 30)

export const today = now.toDate()

export const someDate = LocalDate.of(2020, 5, 30)

export function date(value: string): LocalDate {
    const parts = value.split('/')
    const year = parseInt(parts[2], 10)
    const month = parseInt(parts[1], 10)
    const day = parseInt(parts[0], 10)
    return LocalDate.of(year, month, day)
}

export function dateTime(value: string): LocalDateTime {
    const parts = value.split('/')
    const year = parseInt(parts[2], 10)
    const month = parseInt(parts[1], 10)
    const day = parseInt(parts[0], 10)
    return LocalDateTime.of(year, month, day)
}

export function fullDateTime(value: string): LocalDateTime {
    let valueSplited = value.split(' ')
    const dateParts = valueSplited[0].split('/')
    const timeParts = valueSplited[1].split(':')
    const year = parseInt(dateParts[2], 10)
    const month = parseInt(dateParts[1], 10)
    const day = parseInt(dateParts[0], 10)
    const hours = parseInt(timeParts[0], 10)
    const minutes = parseInt(timeParts[1], 10)
    const seconds = parseInt(timeParts[2], 10)
    return LocalDateTime.of(year, month, day, hours, minutes, seconds)
}
