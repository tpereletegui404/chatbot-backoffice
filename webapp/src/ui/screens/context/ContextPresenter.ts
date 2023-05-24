import { DefaultPresenter } from '../../../core/common/base/presenters/DefaultPresenter'
import { ChangeFunc } from '../../../core/common/base/presenters/ChangeFunc'
import { Core } from '../../../core/Core'
import {CreateContext} from "../../../core/security/app/GetContext"

export class ContextPresenter extends DefaultPresenter<ContextVM> {
    constructor(onChange: ChangeFunc, private core: Core) {
        super(onChange)
        this.model = new ContextVM()
    }

    setContext = context => { this.updateModel({ context: context }); this.resetErrors() }

     createContext = async () => {
        await this.core.execute(new CreateContext(this.model.context))
    }
}

export class ContextVM {
    context = 'Some context Broooooooooooo'
}
