import { Command } from '../../../lib/Command'
import spawnProcess from '../../../lib/spawnProcess'

export default class Stop implements Command {
    name = 'db:test:stop'
    description = 'Stop local test db instance'

    async execute(args?, showUsage?) {
        spawnProcess('docker container stop chatbot-backoffice-db-test')
        spawnProcess('docker container rm chatbot-backoffice-db-test')
    }
}
