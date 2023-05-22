import React, { FC } from 'react'
import { useSession } from './context/SessionContext/SessionContext'
import { useRouter } from 'next/router'
import { useClientSideDetector } from '../services/ClientSideDetector'

export const PrivateRoute: FC<Props> = (props) => {
    const session = useSession()
    const router = useRouter()
    const isClientSide = useClientSideDetector()

    if (!isClientSide || !session) return null

    if (!session.isAuthenticated) {
        router.push('/login')
        return
    }

    return <>{props.children}</>
}

interface Props {
    children: React.ReactNode
}
