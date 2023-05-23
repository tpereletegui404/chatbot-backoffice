import spawnProcessAsync from './spawnProcessAsync'

export default function spawnProcessAsyncOnDir(shellCommand, dir) {
    process.chdir(dir)
    spawnProcessAsync(shellCommand)
    process.chdir('../')
}
