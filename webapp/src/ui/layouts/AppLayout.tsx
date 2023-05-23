import { useSession } from '../components/context/SessionContext/SessionContext'
import { FC, ReactNode } from 'react'
import { useRouter } from 'next/router'
import { AppLayoutConfigProvider } from './AppLayoutConfig'
import { Footer } from './Footer'
import { NavBar } from './NavBar'


export const AppLayout: FC<Props> = (props) => {
    const session = useSession()
    const router = useRouter()
    if (!session?.isAuthenticated) return <>{props.children}</>
    const currentPath = router.pathname
    return (
        <AppLayoutConfigProvider title='' selectedMenuItem={currentPath}>
            <div className='min-h-screen bg-gray-200 flex flex-col'>
                <NavBar />
                <div className='bg-gray-200 flex flex-1'>
                    <div className='relative flex flex-1'>
                        {props.children}
                    </div>
                </div>
            </div>
        </AppLayoutConfigProvider>
    )
}

interface Props {
    children: ReactNode
}
