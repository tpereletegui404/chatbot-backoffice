import { ValidationsError } from '@/core/common/base/validation/ValidationsError'

export async function expectGeneralValidationError(func: () => void, message = '') {
    try {
        await func()
    } catch (e) {
        expectIsValidationError(e)
        const validationError = e as ValidationsError
        expectContainsGeneralMessage(validationError, message)
        return
    }
    // @ts-ignore
    expect(true).forceFailWithMessage('Expecting ValidationError to be thrown but nothing was thrown')
}

export async function expectValidationError(func: () => void, property: string, message = '') {
    try {
        await func()
    } catch (e) {
        expectIsValidationError(e)
        const validationError = e as ValidationsError
        expectContainsProperty(validationError, property)
        expectContainsMessage(validationError, property, message)
        return
    }
    // @ts-ignore
    expect(true).forceFailWithMessage('Expecting ValidationError to be thrown but nothing was thrown')
}

function expectIsValidationError(e) {
    if (!(e instanceof ValidationsError)) {
        // @ts-ignore
        expect(true).forceFailWithMessage(`Expecting ValidationError to be thrown but '${e}' was thrown`)
    }
}

function expectContainsProperty(validationError: ValidationsError, property) {
    if (!validationError.invalidProperties.contains(property)) {
        // @ts-ignore
        expect(true).forceFailWithMessage(`ValidationError was thrown but without property ${property}`)
    }
}

function expectContainsMessage(validationError: ValidationsError, property, message) {
    if (message && validationError.propertyErrors[property] !== message) {
        // @ts-ignore
        expect(true).forceFailWithMessage(`ValidationError was thrown but without property message '${message}' instead was '${validationError.propertyErrors[property]}'`)
    }
}

function expectContainsGeneralMessage(validationError: ValidationsError, message: string) {
    if (!validationError.generalErrorMessages.contains(message)) {
        // @ts-ignore
        expect(true).forceFailWithMessage(`ValidationError was thrown but without general message ${message}. Instead was: ${validationError}`)
    }
}

expect.extend({
    forceFailWithMessage(received, message) {
        return {
            message: () => message,
            pass: false,
        }
    },
})
