import React, { FC } from 'react'
import {useClientSideDetector} from '@/ui/services/ClientSideDetector'

export const OnlyClientSide: FC<Props> = (props) => {
    const isClientSide = useClientSideDetector()
    if (!isClientSide) return null
    return <>{props.children}</>
}

interface Props {
    children: React.ReactNode
}
