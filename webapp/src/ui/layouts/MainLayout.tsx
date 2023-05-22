import { FC, ReactNode } from 'react'
import { SessionProvider } from '../components/context/SessionContext/SessionContext'
import { PageTitle } from '../components/PageTitle'
import { AppLayout } from './AppLayout'

export const MainLayout: FC<Props> = (props) => (
    <SessionProvider>
        <PageTitle />
        <AppLayout>
            {props.children}
        </AppLayout>
    </SessionProvider>
)

interface Props {
    children: ReactNode;
}
