import { Nullable } from '../../common/base/lang/Nullable'

export class Configuration {
    constructor(
        public apiKey: Nullable<string> = null,
        public context: Nullable<string> = null,
        public maxTokens: number,
        public temperature: number,
        public topP: number,
        public frequencyPenalty: number,
        public parameterPresencePenalty: number
    ) {
    }

    static fromJson(json: any): Configuration {
        return new Configuration(
            json.apiKey,
            json.context,
            json.maxTokens,
            json.temperature,
            json.topP,
            json.frequencyPenalty,
            json.parameterPresencePenalty
        )
    }
}
