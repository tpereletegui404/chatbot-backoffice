import { DefaultPresenter } from '../../../../core/common/base/presenters/DefaultPresenter'
import { ChangeFunc } from '../../../../core/common/base/presenters/ChangeFunc'
import { Core } from '../../../../core/Core'
import { Configuration } from '../../../../core/chatbotSettings/model/Configuration'
import { Nullable } from '../../../../core/common/base/lang/Nullable'
import { UpdateConfiguration } from '../../../../core/chatbotSettings/app/UpdateConfiguration'
import { Validator } from '../../../../core/common/base/validation/Validator'
import { ErrorMap } from '../../../../core/common/base/validation/ErrorMap'

export class ParametersPresenter extends DefaultPresenter<ParametersVM> {
    constructor(onChange: ChangeFunc, private core: Core) {
        super(onChange)
        this.model = new ParametersVM()
    }

    start(configuration: Configuration) {
        this.updateModel({...this.model, configuration: this.toVM(configuration)})
    }

    toVM(configuration: Configuration) {
        return {
            apiKey: configuration?.apiKey,
            context: configuration?.context,
            maxTokens: configuration?.maxTokens.toString(),
            temperature: configuration?.temperature.toString(),
            topP: configuration?.topP.toString(),
            frequencyPenalty: configuration?.frequencyPenalty.toString(),
            parameterPresencePenalty: configuration?.parameterPresencePenalty.toString()
        }
    }

    async updateConfig() {
        const configuration = this.model.configuration
        if (!this.validateModel()) return
        await this.core.execute(new UpdateConfiguration(
            configuration.apiKey,
            configuration.context,
            parseInt(configuration.maxTokens),
            parseFloat(configuration.temperature),
            parseFloat(configuration.topP),
            parseInt(configuration.frequencyPenalty),
            parseInt(configuration.parameterPresencePenalty),
        ))
    }

    protected validations(validator: Validator) {
        validator
            .check('temperature', this.model.configuration.temperature)
            .notNullOrBlank('Cannot be blank')
            .number('should be a number')
        validator
            .check('topP', this.model.configuration.topP)
            .notNullOrBlank('Cannot be blank')
            .number('should be a number')
        validator
            .check('maxTokens', this.model.configuration.maxTokens)
            .notNullOrBlank('Cannot be blank')
            .integer('should be an integer')
        validator
            .check('frequencyPenalty', this.model.configuration.frequencyPenalty)
            .notNullOrBlank('Cannot be blank')
            .integer('should be an integer')
        validator
            .check('parameterPresencePenalty', this.model.configuration.parameterPresencePenalty)
            .notNullOrBlank('Cannot be blank')
            .integer('should be an integer')
    }

    setApiKey = apiKey => this.updateModel({...this.model, configuration: {...this.model.configuration, apiKey}})

    setMaxTokens = maxTokens => this.updateModel({...this.model, configuration: {...this.model.configuration, maxTokens}})

    setTemperature = temperature => this.updateModel({...this.model, configuration: {...this.model.configuration, temperature}})

    setTopP = topP => this.updateModel({...this.model, configuration: {...this.model.configuration, topP}})

    setFrequencyPenalty = frequencyPenalty => this.updateModel({...this.model, configuration: {...this.model.configuration, frequencyPenalty}})

    setParameterPresencePenalty = parameterPresencePenalty => this.updateModel({...this.model, configuration: {...this.model.configuration, parameterPresencePenalty}})
}

export class ParametersVM {
    configuration: ConfigurationVM
    errors: ErrorMap = {}
    isLoading = false
}

export interface ConfigurationVM {
    apiKey: Nullable<string>,
    context: Nullable<string>,
    maxTokens: string,
    temperature: string,
    topP: string,
    frequencyPenalty: string,
    parameterPresencePenalty: string
}
