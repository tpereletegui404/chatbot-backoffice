import { DefaultPresenter } from '../../../core/common/base/presenters/DefaultPresenter'
import { ChangeFunc } from '../../../core/common/base/presenters/ChangeFunc'
import { Core } from '../../../core/Core'
import { GetConfiguration } from '../../../core/chatbotSettings/app/GetConfiguration'
import { Configuration } from '../../../core/chatbotSettings/model/Configuration'

export class SettingsPresenter extends DefaultPresenter<SettingsVM> {
    constructor(onChange: ChangeFunc, private core: Core) {
        super(onChange)
        this.model = new SettingsVM()
    }

    async start() {
        await this.withLoader( async () => {
            const configuration = await this.core.execute( new GetConfiguration() )
            this.updateModel({...this.model, configuration: configuration})
        })
    }
}

export class SettingsVM {
    configuration: Configuration
    isLoading = false
}
