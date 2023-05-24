import { Query } from 'asimov-cqbus/dist/requests/Query'
import { Configuration } from '../model/Configuration'

export class GetConfiguration extends Query<Configuration> {
    constructor() { super() }
}
