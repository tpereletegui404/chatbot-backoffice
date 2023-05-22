import { useEffect, useState } from 'react'

export const useClientSideDetector = () => {
    const [isClientSide, setIsClientSide] = useState(false)
    useEffect(() => {
        setIsClientSide(true)
    }, [])
    return isClientSide
}
