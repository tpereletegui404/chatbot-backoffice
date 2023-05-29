import { Core } from '../../../src/core/Core'
import { ModelTracker } from '../../core/common/base/testing/ModelTracker'
import { mockEq } from '../../core/common/base/testing/ts-mockito-extensions'
import { anything, instance, verify, when } from 'ts-mockito'
import { ParametersPresenter } from '../../../src/ui/screens/settings/parameters/ParametersPresenter'
import { UpdateConfiguration } from '../../../src/core/chatbotSettings/app/UpdateConfiguration'
import each from 'jest-each'

describe('on update', () => {
    it('calls update-configuration', async () => {
        await presenter.start(configuration)

        await presenter.updateConfig()

        const config = presenter.model.configuration
        verify(core.execute(new UpdateConfiguration(
            config.apiKey,
            config.context,
            parseInt(config.maxTokens),
            parseFloat(config.temperature),
            parseFloat(config.topP),
            parseInt(config.frequencyPenalty),
            parseInt(config.parameterPresencePenalty)
        ))).called()
    })
})

describe('validations', () => {
    each([
        '', '  ', null,
    ]).it('temperature is required: "%s"', async (value) => {
        presenter.setTemperature(value)

        await presenter.updateConfig()

        expect(presenter.model.errors.temperature).toEqual('Cannot be blank')
    })

    each([
        'some21', 'a1',
    ]).it('temperature must be a valid number: "%s"', async (value) => {
        presenter.setTemperature(value)

        await presenter.updateConfig()

        expect(presenter.model.errors.temperature).toEqual('should be a number')
    })

    each([
        '', '  ', null,
    ]).it('topP is required: "%s"', async (value) => {
        presenter.setTopP(value)

        await presenter.updateConfig()

        expect(presenter.model.errors.topP).toEqual('Cannot be blank')
    })

    each([
        'some21', 'a1',
    ]).it('topP must be a valid number: "%s"', async (value) => {
        presenter.setTopP(value)

        await presenter.updateConfig()

        expect(presenter.model.errors.topP).toEqual('should be a number')
    })

    each([
        '', '  ', null,
    ]).it('maxTokens is required: "%s"', async (value) => {
        presenter.setMaxTokens(value)

        await presenter.updateConfig()

        expect(presenter.model.errors.maxTokens).toEqual('Cannot be blank')
    })

    each([
        'some21', '0.9',
    ]).it('maxTokens must be a valid integer: "%s"', async (value) => {
        presenter.setMaxTokens(value)

        await presenter.updateConfig()

        expect(presenter.model.errors.maxTokens).toEqual('should be an integer')
    })

    each([
        '', '  ', null,
    ]).it('frequencyPenalty is required: "%s"', async (value) => {
        presenter.setFrequencyPenalty(value)

        await presenter.updateConfig()

        expect(presenter.model.errors.frequencyPenalty).toEqual('Cannot be blank')
    })

    each([
        'some21', '0.9',
    ]).it('frequencyPenalty must be a valid integer: "%s"', async (value) => {
        presenter.setFrequencyPenalty(value)

        await presenter.updateConfig()

        expect(presenter.model.errors.frequencyPenalty).toEqual('should be an integer')
    })

    each([
        '', '  ', null,
    ]).it('parameterPresencePenalty is required: "%s"', async (value) => {
        presenter.setParameterPresencePenalty(value)

        await presenter.updateConfig()

        expect(presenter.model.errors.parameterPresencePenalty).toEqual('Cannot be blank')
    })

    each([
        'some21', '0.9',
    ]).it('parameterPresencePenalty must be a valid integer: "%s"', async (value) => {
        presenter.setParameterPresencePenalty(value)

        await presenter.updateConfig()

        expect(presenter.model.errors.parameterPresencePenalty).toEqual('should be an integer')
    })
})

beforeEach(() => {
    core = mockEq(Core)
    modelTracker = new ModelTracker()
    presenter = new ParametersPresenter(modelTracker.onModelChanged, instance(core))
})

let core: Core
let presenter: ParametersPresenter
let modelTracker: ModelTracker
const configuration = {
    apiKey: 'someapikey',
    context: 'some context',
    maxTokens: 2,
    temperature: 0.9,
    topP: 0.9,
    frequencyPenalty: 0,
    parameterPresencePenalty: 0,
}
