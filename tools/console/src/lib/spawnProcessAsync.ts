import { spawn } from 'child_process'

export default function spawnProcessAsync(shellCommand) {
    const commandParts = shellCommand.split(' ')
    const command = commandParts[0]
    const commandArgs = commandParts.slice(1).map(s => s.trim())
    const subprocess = spawn(command, commandArgs, { shell: true, detached: true, stdio: 'ignore' })
    subprocess.unref()
}
