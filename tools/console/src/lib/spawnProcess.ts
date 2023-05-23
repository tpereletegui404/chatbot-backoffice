import { spawnSync } from 'child_process'

export default function spawnProcess(shellCommand, endOnError = true) {
    const commandParts = shellCommand.split(' ')
    const command = commandParts[0]
    const commandArgs = commandParts.slice(1).map(s => s.trim())
    const result = spawnSync(command, commandArgs, { stdio: 'inherit', shell: true })
    if (result.status !== 0 && endOnError) {
        process.exit(result.status)
    }
    return result.status
}
