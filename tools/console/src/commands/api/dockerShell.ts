import { Command } from '../../lib/Command'
import spawnProcess from '../../lib/spawnProcess'

export default class DockerBuild implements Command {
    name = 'api:docker:shell'
    description = 'Docker shell for api'

    async execute(args?, showUsage?) {
        spawnProcess('docker run --rm -it --name chatbot-backoffice-api chatbot-backoffice-api /bin/sh')
    }
}
