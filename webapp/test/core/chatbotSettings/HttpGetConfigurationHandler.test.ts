import { instance, verify, when } from 'ts-mockito'
import { mockEq } from '../common/base/testing/ts-mockito-extensions'
import { HttpClient } from '../../../src/core/common/infrastructure/http/HttpClient'
import {
    HttpGetConfigurationHandler
} from '../../../src/core/chatbotSettings/infrastructure/http/HttpGetConfigurationHandler'
import { GetConfiguration } from '../../../src/core/chatbotSettings/app/GetConfiguration'
import { FakeHttpResponse } from '../common/base/FakeHttpResponse'
import { Configuration } from '../../../src/core/chatbotSettings/model/Configuration'

it('getConfiguration get to /configuration', async () => {
    when(httpClient.get('/configurations')).thenResolve(new FakeHttpResponse(chatbotConfiguration))

    let response = await handler.handle(new GetConfiguration())

    verify(httpClient.get('/configurations')).called()
    expect(response.apiKey).toEqual(chatbotConfiguration.apiKey)
    expect(response.context).toEqual(chatbotConfiguration.context)
    expect(response.temperature).toEqual(chatbotConfiguration.temperature)
    expect(response.topP).toEqual(chatbotConfiguration.topP)
    expect(response.frequencyPenalty).toEqual(chatbotConfiguration.frequencyPenalty)
    expect(response.parameterPresencePenalty).toEqual(chatbotConfiguration.parameterPresencePenalty)
})

beforeEach(() => {
    httpClient = mockEq<HttpClient>()
    handler = new HttpGetConfigurationHandler(instance(httpClient))
})

let httpClient: HttpClient
let handler: HttpGetConfigurationHandler
const chatbotConfiguration = new Configuration(
    'apiKey123',
    'someContext',
    200,
    0.9,
    0.8,
    0,
    0
)