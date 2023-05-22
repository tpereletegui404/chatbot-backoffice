import { DependencyList } from 'react'
import { WebAppServices } from '@/ui/WebApp'
import { useAppServices } from '@/ui/components/context/AppServicesContext'
import { usePresenter } from '@/core/common/base/react/usePresenter'
import { ChangeFunc } from '@/core/common/base/presenters/ChangeFunc'

export function useAppPresenter<TPresenter>(
    presenterFactory: (onChange: ChangeFunc, services: WebAppServices) => TPresenter,
    startArgs: DependencyList = [],
): TPresenter {
    const services = useAppServices()
    return usePresenter(
        (onChange) => services.unhandledErrorDecorator(presenterFactory(onChange, services)),
        startArgs
    )
}
