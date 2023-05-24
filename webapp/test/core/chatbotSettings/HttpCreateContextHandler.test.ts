import { instance, verify } from 'ts-mockito'
import { mockEq } from '../common/base/testing/ts-mockito-extensions'
import { HttpClient } from '../../../src/core/common/infrastructure/http/HttpClient'
import {
    HttpCreateContextHandler
} from '../../../src/core/chatbotSettings/infrastructure/http/HttpCreateContextHandler'
import { UpdateContext } from '../../../src/core/chatbotSettings/app/GetContext'


it('login sends post to /login', async () => {
    await handler.handle(new UpdateContext(context))

    verify(httpClient.post('/context', { context })).called()
})

beforeEach(() => {
    httpClient = mockEq<HttpClient>()
    handler = new HttpCreateContextHandler(instance(httpClient))
})

let httpClient: HttpClient
let handler: HttpCreateContextHandler
const context = 'some context'