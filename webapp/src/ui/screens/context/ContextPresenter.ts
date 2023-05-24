import { DefaultPresenter } from '../../../core/common/base/presenters/DefaultPresenter'
import { ChangeFunc } from '../../../core/common/base/presenters/ChangeFunc'
import { Core } from '../../../core/Core'
import { UpdateContext } from '../../../core/chatbotSettings/app/GetContext'

export class ContextPresenter extends DefaultPresenter<ContextVM> {
    constructor(onChange: ChangeFunc, private core: Core) {
        super(onChange)
        this.model = new ContextVM()
    }

    setContext = context => {
        this.updateModel({ context: context })
        this.resetErrors()
    }

    updateContext = async () => {
        await this.withLoader(async () => {
            await this.core.execute(new UpdateContext(this.model.context))
        })

    }
}

export class ContextVM {
    context = 'Some context Broooooooooooo'
    isLoading = false
}
