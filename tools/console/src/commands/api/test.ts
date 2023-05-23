import spawnProcessOnDir from '../../lib/spawnProcessOnDir';
import chalk from 'chalk';
import { Command } from '../../lib/Command'

export default class Test implements Command {
    name = 'api:test'
    description = 'Test api'

    async execute(args?, showUsage?) {
        console.log(chalk.green.bold('Test api...'))
        const gradle = process.platform.startsWith('win') ? 'gradlew.bat' : './gradlew'
        spawnProcessOnDir(`${gradle} allTest`, 'api')
    }
}
