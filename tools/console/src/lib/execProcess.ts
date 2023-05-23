import { execSync } from 'child_process'

export default function execProcess(shellCommand, endOnError = true) {
    try {
        const result = execSync(shellCommand)
        if (!result) return result
        return result.toString()
    } catch (e) {
        if (endOnError) {
            process.exit(1)
            return
        }
        throw e
    }
}
