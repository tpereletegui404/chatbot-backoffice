import spawnProcessOnDir from '../../lib/spawnProcessOnDir'
import { Command } from '../../lib/Command'
import Purge from '../db/purge'

export default class Populate implements Command {
    name = 'db:populate'
    description = 'Populate db with data from the given scenario'
    parametersUsage = '[-s scenario]'

    async execute(args?, showUsage?) {
        await (new Purge()).execute()
        this.buildPopulate()
        this.populate(args)
    }

    buildPopulate() {
        const gradle = process.platform.startsWith('win') ? 'gradlew.bat' : './gradlew'
        spawnProcessOnDir(gradle + ' tools:populateDb:shadowJar', 'api')
    }

    populate(args) {
        spawnProcessOnDir('java -jar tools/populateDb/build/libs/populateDb.jar ' + args.join(' '), 'api')
    }
}
