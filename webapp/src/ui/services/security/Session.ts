import { Nullable } from '../../../core/common/base/lang/Nullable'
import { User } from '../../../core/security/model/User'

export class Session {
    private _user: Nullable<User> = null
    private _isAuthenticated = false

    get isAuthenticated() { return this._isAuthenticated }

    get user() { return this._user }

    set user(value) {
        this._user = value
    }

    logout() {
        this._user = null
        this._isAuthenticated = false
    }

    authenticate(user: User) {
        this._user = user
        this._isAuthenticated = true
    }

    snapshot() {
        return { isAuthenticated: this._isAuthenticated, user: this._user }
    }

    static fromSnapshot(snapshot): Session {
        const session = new Session()
        session._user = snapshot.user ? User.fromJson(snapshot.user) : null
        session._isAuthenticated = snapshot.isAuthenticated
        return session
    }
}
