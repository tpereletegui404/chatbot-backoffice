import { Session } from './Session'
import { Nullable } from '../../../core/common/base/lang/Nullable'
import { SimpleStorage } from '../../../core/common/infrastructure/simpleStorage/SimpleStorage'
import { Observable } from '../../../core/common/base/lang/Observable'

export class SessionStorage {
    private cachedSession: Nullable<Session> = null
    readonly sessionChanged = new Observable<Session>()

    constructor(private dataStorage: SimpleStorage, private sessionKey = 'current_session') {}

    async update(session: Session) {
        await this.dataStorage.set(this.sessionKey, JSON.stringify(session.snapshot()))
        this.cachedSession = session
        this.sessionChanged.notify(session)
    }

    async get(): Promise<Session> {
        if (this.cachedSession) return this.cachedSession
        let session = await this.tryGet()
        if (session == null) {
            session = new Session()
            await this.update(session)
        }
        this.cachedSession = session
        return session
    }

    private async tryGet(): Promise<Nullable<Session>> {
        const sessionString = await this.dataStorage.get(this.sessionKey)
        if (sessionString === null) return null
        return Session.fromSnapshot(JSON.parse(sessionString))
    }

    async do(func: (session: Session) => void) {
        const session = await this.get()
        func(session)
        await this.update(session)
    }
}
