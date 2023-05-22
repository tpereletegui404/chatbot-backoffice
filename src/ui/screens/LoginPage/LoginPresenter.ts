import { DefaultPresenter } from '@/core/common/base/presenters/DefaultPresenter'
import { ChangeFunc } from '@/core/common/base/presenters/ChangeFunc'
import { Login } from '@/core/security/app/Login'
import { Router } from '@/ui/services/router/Router'
import { Core } from '@/core/Core'
import { SessionStorage } from '@/ui/services/security/SessionStorage'
import { ErrorMap } from '@/core/common/base/validation/ErrorMap'
import { Validator } from '@/core/common/base/validation/Validator'

export class LoginPresenter extends DefaultPresenter<LoginVM> {
    constructor(onChange: ChangeFunc, private core: Core, private router: Router, private sessionStorage: SessionStorage) {
        super(onChange)
        this.model = new LoginVM()
    }

    setUsername = username => { this.updateModel({ username }); this.resetErrors() }

    setPassword = password => { this.updateModel({ password }); this.resetErrors() }

    async login() {
        await this.withLoader(async () => {
            const isValid = this.validateModel()
            if (!isValid) return
            const response = await this.core.execute(new Login(this.model.username, this.model.password))
            if (!response.isSuccessful) return this.showGeneralError('Las credenciales son incorrectas')
            await this.sessionStorage.do(session => session.authenticate(response.user))
            this.router.navigate('/')
        })
    }

    protected validations(validator: Validator) {
        validator.check('username', this.model.username)
            .notNullOrBlank('Ingrese su usuario')
        validator.check('password', this.model.password)
            .notNullOrBlank('Ingrese su contraseña')
    }
}

export class LoginVM {
    username = ''
    password = ''
    isLoading = false
    errors: ErrorMap = {}
}
