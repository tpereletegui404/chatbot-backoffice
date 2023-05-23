import { Command } from '../../lib/Command'
import spawnProcess from '../../lib/spawnProcess'

export default class Stop implements Command {
    name = 'db:stop'
    description = 'Stop local db instance'

    async execute(args?, showUsage?) {
        spawnProcess('docker container stop chatbot-backoffice-db')
        spawnProcess('docker container rm chatbot-backoffice-db')
    }
}
