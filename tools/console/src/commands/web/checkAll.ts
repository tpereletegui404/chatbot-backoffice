import spawnProcessOnDir from '../../lib/spawnProcessOnDir'
import chalk from 'chalk'
import { Command } from '../../lib/Command'

export default class CheckAll implements Command {
    name = 'web:check-all'
    description = 'Check web app'

    async execute(args?, showUsage?) {
        console.log(chalk.green.bold('Checking web...'))
        spawnProcessOnDir('yarn check-all', 'webapp')
    }
}
