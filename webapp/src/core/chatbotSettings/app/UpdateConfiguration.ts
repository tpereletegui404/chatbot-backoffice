import { PureCommand } from 'asimov-cqbus/dist/requests/PureCommand'
import { Nullable } from '../../common/base/lang/Nullable'

export class UpdateConfiguration extends PureCommand {
    constructor(
        public apiKey: Nullable<string>,
        public context: Nullable<string>,
        public maxTokens: number,
        public temperature: number,
        public topP: number,
        public frequencyPenalty: number,
        public parameterPresencePenalty: number,
    ) {
        super()
    }
}
