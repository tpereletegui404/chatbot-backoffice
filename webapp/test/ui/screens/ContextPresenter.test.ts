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
import { ContextPresenter } from '../../../src/ui/screens/settings/context/ContextPresenter'
import { router } from 'next/client'
import { UpdateContext } from '../../../src/core/chatbotSettings/app/UpdateContext'

describe('on update context', () => {
    it('shows loader while updating context', async () => {
        await presenter.updateContext()

        expect(presenter.model.isLoading).toBe(false)
        expect(modelTracker.changes.some((change) => change.isLoading)).toBe(true)
    })

    it('calls update context action', async () => {
        await presenter.updateContext()

        verify(core.execute(new UpdateContext(context))).called()
    })

    beforeEach(() => {
        presenter.setContext(context)
    })
})

beforeEach(() => {
    core = mockEq(Core)
    modelTracker = new ModelTracker()
    presenter = new ContextPresenter(modelTracker.onModelChanged, instance(core))
    when(core.execute(anything())).thenResolve()
})

let core: Core
let presenter: ContextPresenter
let modelTracker: ModelTracker
const context = 'some context'
