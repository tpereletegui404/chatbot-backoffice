/* eslint-disable eqeqeq */
import { Validator } from './Validator'
import { isNullOrBlank } from './validationFunctions'

export class PropertyAssertions {
    private readonly name: string
    private readonly value: any
    private validator: Validator
    private hasPreviousErrors = false

    constructor(name: string, value: any, validator: Validator) {
        this.name = name
        this.value = value
        this.validator = validator
    }

    notNullOrEmpty(errorMessage?: string): PropertyAssertions {
        const isValid = this.value !== null && this.value !== ''
        this.processValidation(isValid, errorMessage || (this.name + ' es requerido'))
        return this
    }

    notNullOrBlank(errorMessage?: string): PropertyAssertions {
        const isValid = !isNullOrBlank(this.value)
        this.processValidation(isValid, errorMessage || (this.name + ' es requerido'))
        return this
    }

    notNull(errorMessage?: string): PropertyAssertions {
        const isValid = this.value !== null
        this.processValidation(isValid, errorMessage || (this.name + ' es requerido'))
        return this
    }

    number(errorMessage?: string): PropertyAssertions {
        const isValid =
            !Number.isNaN(parseFloat(this.value)) &&
            Number.isFinite(parseFloat(this.value)) &&
            this.value.toString().countOccurrences('.') <= 1
        this.processValidation(isValid, errorMessage || (this.name + ' debe ser un número'))
        return this
    }

    integer(errorMessage?: string): PropertyAssertions {
        const isValid =
            !Number.isNaN(parseInt(this.value, 10)) &&
            Number.isFinite(parseInt(this.value, 10)) &&
            parseInt(this.value, 10).toString() == this.value
        this.processValidation(isValid, errorMessage || (this.name + ' debe ser un número entero'))
        return this
    }

    positive(errorMessage?: string): PropertyAssertions {
        const isValid = this.value > 0
        this.processValidation(isValid, errorMessage || (this.name + ' debe ser positivo'))
        return this
    }

    zeroOrPositive(errorMessage?: string): PropertyAssertions {
        const isValid = this.value == 0 || this.value > 0
        this.processValidation(isValid, errorMessage || (this.name + ' debe ser positivo'))
        return this
    }

    equals(otherValue: any, errorMessage?: string): PropertyAssertions {
        const isValid = this.value === otherValue
        this.processValidation(isValid, errorMessage || (`${this.value} no es igual a ${otherValue}`))
        return this
    }

    email(errorMessage?: string): PropertyAssertions {
        // eslint-disable-next-line no-useless-escape
        const emailRegex = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
        const isValid = emailRegex.test(this.value)
        this.processValidation(isValid, errorMessage || (this.name + ' debe ser un email válido'))
        return this
    }

    minLength(min: number, errorMessage?: string): PropertyAssertions {
        const isValid = this.value.toString().length >= min
        this.processValidation(isValid, errorMessage || (`${this.name} debe tener al menos ${min} caracteres`))
        return this
    }

    maxLength(max: number, errorMessage?: string): PropertyAssertions {
        const isValid = this.value.toString().length <= max
        this.processValidation(isValid, errorMessage || (`${this.name} debe tener menos de ${max} caracteres`))
        return this
    }

    date(format: string, errorMessage?: string): PropertyAssertions {
        let isValid = true
        if (format === 'DD/MM/YYYY') {
            isValid = this.isValidDDMMYYYYDate('/')
        } else if(format === 'YYYY-MM-DD') {
            isValid = this.isValidYYYYMMDDDate('-')
        } else {
            throw new Error(`Unsupported date format: ${format}`)
        }
        this.processValidation(isValid, errorMessage || (`${this.name} debe ser una fecha válida`))
        return this
    }

    minDate(format: string, minDate: Date, errorMessage?: string): PropertyAssertions {
        let isValid = true
        if (format === 'DD/MM/YYYY') {
            const value = this.toDateFromDDMMYYYY('/')
            isValid = value.getTime() >= minDate.getTime()
        } else if (format === 'YYYY/MM/DD') {
            const value = this.toDateFromYYYYMMDD('-')
            isValid = value.getTime() >= minDate.getTime()
        } else {
            throw new Error(`Unsupported date format: ${format}`)
        }
        this.processValidation(isValid, errorMessage || (`${this.name} debe ser una fecha válida`))
        return this
    }

    maxDate(format: string, maxDate: Date, errorMessage?: string): PropertyAssertions {
        let isValid = true
        if (format === 'DD/MM/YYYY') {
            const value = this.toDateFromDDMMYYYY('/')
            isValid = value.getTime() <= maxDate.getTime()
        } else if (format === 'YYYY/MM/DD') {
            const value = this.toDateFromYYYYMMDD('-')
            isValid = value.getTime() <= maxDate.getTime()
        } else {
            throw new Error(`Unsupported date format: ${format}`)
        }
        this.processValidation(isValid, errorMessage || (`${this.name} debe ser una fecha válida`))
        return this
    }

    private processValidation(isValid: boolean, errorMessage: string) {
        if (this.hasPreviousErrors) { return }
        if (!isValid) {
            this.hasPreviousErrors = true
            this.validator.addPropertyError(this.name, errorMessage)
        }
    }

    private isValidDDMMYYYYDate(separator: string) {
        if (!this.value) return false
        const parsedDate = this.value.split(separator)
        const date = this.toDateFromDDMMYYYY(separator)
        return date.getDate() == parseInt(parsedDate[0], 10) &&
            date.getMonth() == parseInt(parsedDate[1], 10) - 1 &&
            date.getFullYear() == parseInt(parsedDate[2], 10)
    }

    private isValidYYYYMMDDDate(separator: string) {
        if (!this.value) return false
        const parsedDate = this.value.split(separator)
        const date = this.toDateFromYYYYMMDD(separator)
        return date.getDate() == parseInt(parsedDate[2], 10) &&
            date.getMonth() == parseInt(parsedDate[1], 10) - 1 &&
            date.getFullYear() == parseInt(parsedDate[0], 10)
    }

    private toDateFromDDMMYYYY(separator: string) {
        const parsedDate = this.value.split(separator)
        return new Date(parsedDate[2], parsedDate[1] - 1, parsedDate[0])
    }

    private toDateFromYYYYMMDD(separator: string) {
        const parsedDate = this.value.split(separator)
        return new Date(parsedDate[0], parsedDate[1] - 1, parsedDate[2])
    }
}
