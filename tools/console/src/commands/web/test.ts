import spawnProcessOnDir from '../../lib/spawnProcessOnDir'
import chalk from 'chalk'
import { Command } from '../../lib/Command'

export default class Test implements Command {
    name = 'web:test'
    description = 'Test web app'

    async execute(args?, showUsage?) {
        console.log(chalk.green.bold('Test web app...'))
        spawnProcessOnDir('yarn check-all', 'webapp')
    }
}
