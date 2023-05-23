import { Command } from '../../lib/Command'
import spawnProcess from '../../lib/spawnProcess'

export default class DockerBuild implements Command {
    name = 'api:docker:build'
    description = 'Build api docker image'

    async execute(args?, showUsage?) {
        spawnProcess('docker build --tag chatbot-backoffice-api --target builder ./api')
    }
}
