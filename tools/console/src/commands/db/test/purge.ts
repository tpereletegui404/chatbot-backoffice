import DbTestStart from './start'
import { Command } from '../../../lib/Command'
import spawnProcess from '../../../lib/spawnProcess'

export default class Purge implements Command {
    name = 'db:test:purge'
    description = 'Purge local test db instance and restart it'

    async execute(args?, showUsage?) {
        spawnProcess('docker container stop chatbot-backoffice-db-test', false)
        spawnProcess('docker container rm chatbot-backoffice-db-test', false)
        spawnProcess('docker volume rm chatbot-backoffice-test-db-data', false)
        const dbTestStart = new DbTestStart()
        dbTestStart.execute()
    }
}
