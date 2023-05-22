import { ValidationError } from './ValidationError'
import { PropertyValidationError } from './PropertyValidationError'
import { GeneralValidationError } from './GeneralValidationError'
import { PropertyAssertions } from './PropertyAssertions'
import { ErrorMap } from '@/core/common/base/validation/ErrorMap'

export class Validator {
    private _errors: ValidationError[] = []

    check(propertyName: string, propertyValue: any): PropertyAssertions {
        return new PropertyAssertions(propertyName, propertyValue, this)
    }

    addGeneralError(errorMessage: string): Validator {
        this._errors.push(new GeneralValidationError(errorMessage))
        return this
    }

    addPropertyError(name: string, errorMessage: string): Validator {
        this._errors.push(new PropertyValidationError(name, errorMessage))
        return this
    }

    hasErrors(): boolean {
        return this._errors.length > 0
    }

    get hasGeneralErrors(): boolean {
        return this._errors.some((error) => error instanceof GeneralValidationError)
    }

    get propertyErrors(): { [property: string]: string } {
        return this._errors
            .filter((error) => error instanceof PropertyValidationError)
            .reduce((result, error) => {
                const propertyError = (error as PropertyValidationError)
                if (!result[propertyError.propertyName]) {
                    result[propertyError.propertyName] = propertyError.message
                }
                return result
            }, {})
    }

    get generalErrorMessages(): string[] {
        return this._errors
            .filter((error) => error instanceof GeneralValidationError)
            .map((error) => error.message)
    }

    allErrorMessages(generalErrorsKey = 'general'): ErrorMap {
        const errors = this.propertyErrors
        if (this.hasGeneralErrors) {
            errors[generalErrorsKey] = this.generalErrorMessages.first()
        }
        return errors
    }
}
