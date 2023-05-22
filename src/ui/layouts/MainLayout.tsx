import { FC, ReactNode } from 'react'
import { SessionProvider } from '@/ui/components/context/SessionContext/SessionContext'
import { PageTitle } from '@/ui/components/PageTitle'
import { AppLayout } from '@/ui/layouts/AppLayout'

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
