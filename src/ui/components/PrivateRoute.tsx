import React, { FC } from 'react'
import { useSession } from '@/ui/components/context/SessionContext/SessionContext'
import { useRouter } from 'next/router'
import { useClientSideDetector } from '@/ui/services/ClientSideDetector'

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
