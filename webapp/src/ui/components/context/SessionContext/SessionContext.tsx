import { createContext, FC, ReactNode, useContext } from 'react'
import { Session } from '../../../services/security/Session'
import { Nullable } from '../../../../core/common/base/lang/Nullable'
import { WebAppServices } from '../../../WebApp'
import { SessionContextPresenter } from './SessionContextPresenter'
import { useAppPresenter } from '../../hooks/useAppPresenter'

export const SessionContext = createContext<Nullable<Session>>(null)

const sessionContextPresenter = (onChange, services: WebAppServices) =>
    new SessionContextPresenter(onChange, services.sessionStorage)

export const SessionProvider: FC<Props> = (props) => {
    const presenter = useAppPresenter(sessionContextPresenter)
    return (
        <SessionContext.Provider value={presenter.model}>
            {props.children}
        </SessionContext.Provider>
    )
}

export const useSession = () => useContext(SessionContext)

interface Props {
    children: ReactNode
}
