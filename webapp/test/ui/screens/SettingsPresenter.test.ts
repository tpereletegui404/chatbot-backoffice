import { Core } from '../../../src/core/Core'
import { ModelTracker } from '../../core/common/base/testing/ModelTracker'
import { mockEq } from '../../core/common/base/testing/ts-mockito-extensions'
import { anything, instance, verify, when } from 'ts-mockito'
import { SettingsPresenter } from '../../../src/ui/screens/settings/SettingsPresenter'
import { GetConfiguration } from '../../../src/core/chatbotSettings/app/GetConfiguration'

describe('on start', () => {
    it('shows loader on start', async () => {
        await presenter.start()

        expect(presenter.model.isLoading).toBe(false)
        expect(modelTracker.changes.some((change) => change.isLoading)).toBe(true)
    })

    it('calls get configuration action', async () => {
        await presenter.start()

        verify(core.execute(new GetConfiguration())).called()
    })

    beforeEach(() => {
    })
})

beforeEach(() => {
    core = mockEq(Core)
    modelTracker = new ModelTracker()
    presenter = new SettingsPresenter(modelTracker.onModelChanged, instance(core))
    when(core.execute(anything())).thenResolve()
})

let core: Core
let presenter: SettingsPresenter
let modelTracker: ModelTracker