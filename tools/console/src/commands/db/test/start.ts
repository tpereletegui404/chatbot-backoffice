import { Command } from '../../../lib/Command'
import spawnProcess from '../../../lib/spawnProcess'

export default class Start implements Command {
    name = 'db:test:start'
    description = 'Start local test db instance'

    async execute(args?, showUsage?) {
        spawnProcess('docker volume create chatbot-backoffice-test-db-data')
        spawnProcess('docker run --name chatbot-backoffice-db-test --restart always -e POSTGRES_PASSWORD=1234 -e POSTGRES_DB=chatbot-backoffice-test -p 5633:5432 -v chatbot-backoffice-test-db-data:/var/lib/postgresql/data -d postgres:12-alpine', false)
    }
}
