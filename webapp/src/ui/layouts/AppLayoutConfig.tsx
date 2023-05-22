import React, { createContext, FC, ReactNode, useContext, useEffect, useState } from 'react'
import { useRouter } from 'next/router'
import { PageTitle } from '../components/PageTitle'

export const AppLayoutConfigContext = createContext(undefined)

export const AppLayoutConfigProvider: FC<ProviderProps> = (props) => {
    const router = useRouter()
    const [title, setTitle] = useState(undefined)
    const [back, setBack] = useState(undefined)
    const [backPath, setBackPath] = useState(undefined)
    const [actions, setActions] = useState(undefined)
    const [selectedMenuItem, setSelectedMenuItem] = useState(undefined)
    useEffect(() => {
        setTitle(undefined)
        setBack(undefined)
        setBackPath(undefined)
        setActions(undefined)
        setSelectedMenuItem(undefined)
    }, [router.pathname])
    return (
        <AppLayoutConfigContext.Provider value={{
            title: title ?? props.title,
            setTitle,
            back,
            setBack,
            backPath,
            setBackPath,
            actions,
            setActions,
            selectedMenuItem: selectedMenuItem ?? props.selectedMenuItem,
            setSelectedMenuItem,
        }}>
            {props.children}
        </AppLayoutConfigContext.Provider>
    )
}

export const AppLayoutConfig: FC<OverridePageHeaderProps> = (props) => {
    const { setTitle, setSelectedMenuItem, setActions, setBackPath, setBack } = useContext(AppLayoutConfigContext)
    useEffect(() => {
        if (props.title) setTitle(props.title)
        if (props.actions) setActions(props.actions)
        if (props.back) setBack(props.back)
        if (props.backPath) setBackPath(props.backPath)
        if (props.selectedMenuItem) setSelectedMenuItem(props.selectedMenuItem)
    })
    return <>{props.title && <PageTitle title={props.title} />}</>
}

interface ProviderProps {
    title: string
    selectedMenuItem: string
    children: ReactNode
}

interface OverridePageHeaderProps {
    title?: string
    selectedMenuItem?: string
    actions?: ReactNode
    backPath?: string
    back?: boolean
}
