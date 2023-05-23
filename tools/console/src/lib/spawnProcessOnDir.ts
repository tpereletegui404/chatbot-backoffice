import spawnProcess from './spawnProcess'

export default function spawnProcessOnDir(shellCommand, dir, endOnError = true) {
    process.chdir(dir)
    const result = spawnProcess(shellCommand, endOnError)
    process.chdir('../')
    return result
}
