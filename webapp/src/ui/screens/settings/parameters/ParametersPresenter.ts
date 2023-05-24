import { DefaultPresenter } from '../../../../core/common/base/presenters/DefaultPresenter'
import { ChangeFunc } from '../../../../core/common/base/presenters/ChangeFunc'
import { Core } from '../../../../core/Core'
import { Configuration } from '../../../../core/chatbotSettings/model/Configuration'

export class ParametersPresenter extends DefaultPresenter<ParametersVM> {
    constructor(onChange: ChangeFunc, private core: Core) {
        super(onChange)
        this.model = new ParametersVM()
    }

    start(configuration: Configuration) {
        this.updateModel({...this.model, configuration: configuration})
    }
}

export class ParametersVM {
    configuration: Configuration
    isLoading = false
}
