import { Command } from '../../lib/Command'
import spawnProcess from '../../lib/spawnProcess'
import { waitForPostgres } from '@jcoreio/wait-for-postgres'

export default class Start implements Command {
    name = 'db:start'
    description = 'Start local db instance'

    async execute(args?, showUsage?) {
        const port = 5632
        const database = 'chatbot-backoffice'
        const password = '1234'

        spawnProcess('docker volume create chatbot-backoffice-db-data', false)
        spawnProcess(`docker run --name chatbot-backoffice-db --restart always -e POSTGRES_PASSWORD=${password} -e POSTGRES_DB=${database} -p ${port}:5432 -v chatbot-backoffice-db-data:/var/lib/postgresql/data -d postgres:12-alpine`, false)
        await waitForPostgres({
            host: 'localhost',
            port,
            database,
            user: 'postgres',
            password,
            query: 'SELECT 1',
            timeout: 30 * 1000 // milliseconds
        })
    }
}
