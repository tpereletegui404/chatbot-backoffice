import spawnProcessOnDir from '../../lib/spawnProcessOnDir'
import { Command } from '../../lib/Command'

export default class Start implements Command {
    name = 'web:start:dev'
    description = 'Runs web app'
    alias = 'web'
    isCommon = true

    async execute(args?, showUsage?) {
        spawnProcessOnDir('yarn dev', 'webapp')
    }
}
