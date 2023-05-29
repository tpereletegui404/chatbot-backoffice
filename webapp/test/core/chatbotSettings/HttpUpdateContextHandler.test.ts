import { instance, verify } from 'ts-mockito'
import { mockEq } from '../common/base/testing/ts-mockito-extensions'
import { HttpClient } from '../../../src/core/common/infrastructure/http/HttpClient'
import {
    HttpUpdateContextHandler
} from '../../../src/core/chatbotSettings/infrastructure/http/HttpUpdateContextHandler'
import { UpdateContext } from '../../../src/core/chatbotSettings/app/UpdateContext'


it('login sends post to /login', async () => {
    await handler.handle(new UpdateContext(context))

    verify(httpClient.post('/configurations/context', { context })).called()
})

beforeEach(() => {
    httpClient = mockEq<HttpClient>()
    handler = new HttpUpdateContextHandler(instance(httpClient))
})

let httpClient: HttpClient
let handler: HttpUpdateContextHandler
const context = 'some context'