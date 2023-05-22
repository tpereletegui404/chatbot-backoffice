import { usernames } from './UserExamples'
import { User } from '@/core/security/model/User'

let nextId = 1

export class UserBuilder {
    private _id = nextId++
    private _username = usernames.one()

    username(value: string) {
        this._username = value
        return this
    }

    build = () => new User(this._id, this._username)
}

export function newUser(changes: (builder: UserBuilder) => void = () => {}): User {
    const builder = new UserBuilder()
    changes(builder)
    return builder.build()
}
