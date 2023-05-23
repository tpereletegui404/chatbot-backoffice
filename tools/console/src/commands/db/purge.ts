import DbStart from './start'
import { Command } from '../../lib/Command'
import spawnProcess from '../../lib/spawnProcess'

export default class Purge implements Command {
    name = 'db:purge'
    description = 'Purge local db instance and restart it'

    async execute(args?, showUsage?) {
        spawnProcess('docker container stop chatbot-backoffice-db', false)
        spawnProcess('docker container rm chatbot-backoffice-db', false)
        spawnProcess('docker volume rm chatbot-backoffice-db-data', false)
        const dbStart = new DbStart();
        await dbStart.execute()
    }
}
