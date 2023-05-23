import spawnProcessOnDir from '../lib/spawnProcessOnDir'
import DbStart from './db/start'
import { Command } from '../lib/Command'

export default class Init implements Command {
    name = 'init'
    description = 'Init all apps'

    async execute(args?, showUsage?) {
        spawnProcessOnDir('yarn install', 'webapp')
        await new DbStart().execute([], showUsage)
    }
}
