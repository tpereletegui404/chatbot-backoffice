import { DefaultPresenter } from '../../../../core/common/base/presenters/DefaultPresenter'
import { ChangeFunc } from '../../../../core/common/base/presenters/ChangeFunc'
import { Core } from '../../../../core/Core'
import { UpdateContext } from '../../../../core/chatbotSettings/app/UpdateContext'

export class ContextPresenter extends DefaultPresenter<ContextVM> {
    constructor(onChange: ChangeFunc, private core: Core) {
        super(onChange)
        this.model = new ContextVM()
    }

    start(context) {
        this.updateModel({...this.model, context: context})
    }

    setContext = context => {
        this.updateModel({ context: context })
        this.resetErrors()
    }

    updateContext = async () => {
        await this.withLoader(async () => {
            await this.core.execute(new UpdateContext(this.model.context))
            this.start(this.model.context)
        })

    }
}

export class ContextVM {
    context = ''
    isLoading = false
}
