import { ChangeFunc } from '../../../../core/common/base/presenters/ChangeFunc'
import { Session } from '../../../services/security/Session'
import { SessionStorage } from '../../../services/security/SessionStorage'
import { Nullable } from '../../../../core/common/base/lang/Nullable'

export class SessionContextPresenter {
    private _model: Nullable<Session>

    constructor(private onChange: ChangeFunc, private sessionStorage: SessionStorage) {
        this._model = null
        this.sessionStorage.sessionChanged.subscribe(this, (session) => this.updateModel(session))
    }

    get model() {
        return this._model
    }

    async start() {
        this.updateModel(await this.sessionStorage.get())
    }

    private updateModel(model) {
        this._model = model
        this.onChange(this._model)
    }

    stop() {
        this.sessionStorage.sessionChanged.unsubscribe(this)
    }
}
