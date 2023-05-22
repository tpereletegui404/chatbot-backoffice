import { User } from '@/core/security/model/User'
import { Nullable } from '@/core/common/base/lang/Nullable'
import { Command } from 'asimov-cqbus/dist/requests/Command'

export class Login extends Command<LoginResponse> {
    constructor(public username: string, public password: string) { super() }
}

export interface LoginResponse {
    isSuccessful: boolean,
    user: Nullable<User>,
}
