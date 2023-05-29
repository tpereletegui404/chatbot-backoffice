import { instance, verify, when } from 'ts-mockito'
import { mockEq } from '../common/base/testing/ts-mockito-extensions'
import { HttpClient } from '../../../src/core/common/infrastructure/http/HttpClient'
import {
    HttpUpdateConfigurationHandler
} from '../../../src/core/chatbotSettings/infrastructure/http/HttpUpdateConfigurationHandler'
import { UpdateConfiguration } from '../../../src/core/chatbotSettings/app/UpdateConfiguration'


it('handle should put to /configurations', async () => {
    when(httpClient.put(`/configurations`, chatbotConfigurationRequest)).thenResolve()

    await handler.handle(updateConfiguration())

    verify(httpClient.put('/configurations', chatbotConfigurationRequest)).called()
})

beforeEach(() => {
    httpClient = mockEq<HttpClient>()
    handler = new HttpUpdateConfigurationHandler(instance(httpClient))
})

function updateConfiguration(): UpdateConfiguration {
    return new UpdateConfiguration(
        chatbotConfigurationRequest.apiKey,
        chatbotConfigurationRequest.context,
        chatbotConfigurationRequest.maxTokens,
        chatbotConfigurationRequest.temperature,
        chatbotConfigurationRequest.topP,
        chatbotConfigurationRequest.frequencyPenalty,
        chatbotConfigurationRequest.parameterPresencePenalty
    )
}

let httpClient: HttpClient
let handler: HttpUpdateConfigurationHandler
const chatbotConfigurationRequest = {
    apiKey: 'someapikey',
    context: 'some context',
    maxTokens: 2,
    temperature: 0.9,
    topP: 0.9,
    frequencyPenalty: 0,
    parameterPresencePenalty: 0,
}