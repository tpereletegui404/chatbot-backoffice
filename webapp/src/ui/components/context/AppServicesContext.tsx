import React, { FC, ReactNode, useContext } from 'react'
import { WebAppServices } from '../../WebApp'

// @ts-ignore
export const AppServicesContext = React.createContext<WebAppServices>(undefined)

export const AppServicesProvider: FC<Props> = (props) => (
    <AppServicesContext.Provider value={props.services}>
        {props.children}
    </AppServicesContext.Provider>
)

export const useAppServices = () => useContext(AppServicesContext)

interface Props {
    services: WebAppServices,
    children: ReactNode,
}
