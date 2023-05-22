import { HttpLoginHandler } from '@/core/security/infrastructure/http/HttpLoginHandler'
import { HttpClient } from '@/core/common/infrastructure/http/HttpClient'
import { AnonymousIdentity } from 'asimov-cqbus/dist/identity/AnonymousIdentity'
import { Login, LoginResponse } from '@/core/security/app/Login'
import { newUser } from '../../models/UserBuilder'
import { mockEq } from '../../../common/base/testing/ts-mockito-extensions'
import { anything, instance, verify, when } from 'ts-mockito'
import { admin } from '../../models/UserExamples'
import { FakeHttpResponse } from '../../../common/base/FakeHttpResponse'

it('login sends post to /login', async () => {
    await handler.handle(new Login(admin.username, admin.password), anonymousIdentity)

    verify(httpClient.post('/login', { username: admin.username, password: admin.password })).called()
})

it('returns admin data on success', async () => {
    when(httpClient.post('/login', anything())).thenResolve(new FakeHttpResponse(SUCCESSFUL_RESPONSE))

    const response = await handler.handle(sysadminLogin, anonymousIdentity)

    expect(response.isSuccessful).toBe(true)
    expect(response.user.id).toBe(SUCCESSFUL_RESPONSE.user.id)
    expect(response.user.username).toBe(SUCCESSFUL_RESPONSE.user.username)
})

it('returns response on unsuccessful login', async () => {
    when(httpClient.post('/login', anything())).thenResolve(new FakeHttpResponse(UNSUCCESSFUL_RESPONSE))

    const response = await handler.handle(sysadminLogin, anonymousIdentity)

    expect(response.isSuccessful).toBe(false)
    expect(response.user).toBeNull()
})

beforeEach(() => {
    httpClient = mockEq<HttpClient>()
    handler = new HttpLoginHandler(instance(httpClient))
    when(httpClient.post(anything(), anything())).thenResolve(new FakeHttpResponse(SUCCESSFUL_RESPONSE))
})

let httpClient: HttpClient
let handler: HttpLoginHandler

const SUCCESSFUL_RESPONSE: LoginResponse = { isSuccessful: true, user: newUser() }
const UNSUCCESSFUL_RESPONSE: LoginResponse = { isSuccessful: false, user: null }
const sysadminLogin = new Login(admin.username, admin.password)
const anonymousIdentity = new AnonymousIdentity()
