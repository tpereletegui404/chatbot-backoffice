import { LocalDateTime } from '../../../core/common/base/time/LocalDateTime'
import { LocalDate } from '../../../core/common/base/time/LocalDate'
import { Nullable } from '../../../core/common/base/lang/Nullable'

export class DateFormatter {
    static formatDateTime(dateTime: Nullable<LocalDateTime>) {
        if (!dateTime) return ''
        const day = dateTime.day.toString().padZeros(2)
        const month = dateTime.month.toString().padZeros(2)
        const year = dateTime.year.toString()
        const hours = dateTime.hours.toString().padZeros(2)
        const minutes = dateTime.minutes.toString().padZeros(2)

        return `${day}/${month}/${year} ${hours}:${minutes}`
    }

    static formatDate(date: Nullable<LocalDate>) {
        if (!date) return ''
        const day = date.day.toString().padZeros(2)
        const month = date.month.toString().padZeros(2)
        const year = date.year.toString()

        return `${day}/${month}/${year}`
    }
}
