import ApiTest from './api/test'
import WebAppCheckAll from './web/checkAll'
import { Command } from '../lib/Command'

export default class Test implements Command {
    name = 'test'
    description = 'Test all apps'
    isCommon = true

    async execute(args?, showUsage?) {
        await (new WebAppCheckAll()).execute([], showUsage)
        await (new ApiTest()).execute([], showUsage)
    }
}
