import { ValidationsError } from './ValidationsError'

export function handleValidationErrors(e: Error, onValidationError: (error) => void) {
    if (e instanceof ValidationsError) {
        onValidationError(e)
        return
    }
    throw e
}
