import { ChangeFunc } from '@/core/common/base/presenters/ChangeFunc'
import { Validator } from '@/core/common/base/validation/Validator'

export abstract class DefaultPresenter<TModel = any> {
    protected _model: TModel

    protected constructor(protected onChange: ChangeFunc) {
        this._model = {} as TModel
    }

    get model(): TModel {
        return this._model
    }

    protected set model(value: TModel) {
        this._model = value
    }

    protected updateModel<K extends keyof TModel>(changes: Pick<TModel, K>): void {
        this._model = {...this.model, ...changes}
        this.onChange(this._model)
    }

    protected validateModel(): boolean {
        if (!('errors' in this.model)) throw new Error('Unsupported model')
        const validator = new Validator()
        this.validations(validator)
        const isValid = !validator.hasErrors()
        if (!isValid) {
            // @ts-ignore
            this.updateModel({errors: validator.allErrorMessages()})
        }
        return isValid
    }

    protected validations(validator: Validator) {
    }

    protected showError(propertyName: string, message: string) {
        // @ts-ignore
        if (!('errors' in this.model) || !(this.model.errors instanceof Object)) throw new Error('Unsupported model')
        // @ts-ignore
        this.updateModel({errors: {[propertyName]: message}})
    }

    protected showGeneralError(message: string) {
        this.showError('general', message)
    }

    protected resetErrors() {
        // @ts-ignore
        this.updateModel({ errors: {} })
    }

    resetSuccessMessage() {
        // @ts-ignore
        this.updateModel({ successMessage: '' })
    }

    protected async withLoader(block: () => Promise<any>) {
        if (!('isLoading' in this.model)) throw new Error('Unsupported model')
        // @ts-ignore
        this.updateModel({isLoading: true})
        try {
            await block()
        } finally {
            // @ts-ignore
            this.updateModel({isLoading: false})
        }
    }
}
