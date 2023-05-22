import { Core } from '../../../src/core/Core'
import { LoginPresenter } from '../../../src/ui/screens/LoginPage/LoginPresenter'
import { ModelTracker } from '../../core/common/base/testing/ModelTracker'
import { SessionStorage } from '../../../src/ui/services/security/SessionStorage'
import { InMemorySimpleStorage } from '../../../src/core/common/infrastructure/simpleStorage/InMemorySimpleStorage'
import { mockEq } from '../../core/common/base/testing/ts-mockito-extensions'
import { anything, instance, verify, when } from 'ts-mockito'
import { Router } from '../../../src/ui/services/router/Router'
import { Login } from '../../../src/core/security/app/Login'
import { admin } from '../../core/security/models/UserExamples'
import { newUser } from '../../core/security/models/UserBuilder'
import each from 'jest-each'

describe('on login', () => {
    it('shows loader while authenticating user', async () => {
        await presenter.login()

        expect(presenter.model.isLoading).toBe(false)
        expect(modelTracker.changes.some((change) => change.isLoading)).toBe(true)
    })

    it('calls login action', async () => {
        await presenter.login()

        verify(core.execute(new Login(admin.username, admin.password))).called()
    })

    it('authenticates user when is successful', async () => {
        const user = newUser()
        const response = { user, isSuccessful: true }
        when(core.execute(new Login(anything(), anything()))).thenResolve(response)

        await presenter.login()

        const session = await sessionStorage.get()
        expect(session.isAuthenticated).toEqual(true)
        expect(session.user).toEqual(user)
    })

    it('shows credentials error when is unsuccessful', async () => {
        when(core.execute(new Login(anything(), anything()))).thenResolve(UNSUCCESSFUL_RESPONSE)

        await presenter.login()

        expect(presenter.model.errors).toEqual({ general: 'Las credenciales son incorrectas' })
    })

    it('navigates to dashboard when is successful', async () => {
        when(core.execute(new Login(anything(), anything()))).thenResolve(SUCCESSFUL_RESPONSE)

        await presenter.login()

        verify(router.navigate('/')).called()
    })

    beforeEach(() => {
        presenter.setUsername(admin.username)
        presenter.setPassword(admin.password)
    })
})

describe('validations', () => {
    each([
        '', '  ', null,
    ]).it('username is required: "%s"', async (value) => {
        presenter.setUsername(value)

        await presenter.login()

        expect(presenter.model.errors.username).toEqual('Ingrese su usuario')
    })

    each([
        '', '  ', null,
    ]).it('password is required: "%s"', async (value) => {
        presenter.setPassword(value)

        await presenter.login()

        expect(presenter.model.errors.password).toEqual('Ingrese su contraseña')
    })
})

beforeEach(() => {
    sessionStorage = new SessionStorage(new InMemorySimpleStorage())
    core = mockEq(Core)
    router = mockEq<Router>()
    modelTracker = new ModelTracker()
    presenter = new LoginPresenter(modelTracker.onModelChanged, instance(core), instance(router), sessionStorage)
    when(core.execute(anything())).thenResolve(SUCCESSFUL_RESPONSE)
})

const SUCCESSFUL_RESPONSE = { isSuccessful: true, user: newUser(user => user.username(admin.username)) }
const UNSUCCESSFUL_RESPONSE = { isSuccessful: false, user: null }
let core: Core
let presenter: LoginPresenter
let modelTracker: ModelTracker
let sessionStorage: SessionStorage
let router: Router
