import {useState} from "react"
import {ContextPage} from "./context/ContextPage"
import { WebAppServices } from '../../WebApp'
import { ContextPresenter } from './context/ContextPresenter'
import { SettingsPresenter } from './SettingsPresenter'
import { useAppPresenter } from '../../components/hooks/useAppPresenter'
import { ParametersPage } from './parameters/ParametersPage'

const settingsPresenter = (onChange, services: WebAppServices) =>
    new SettingsPresenter(onChange, services.core)

export const SettingsPage = () => {
    const presenter = useAppPresenter(settingsPresenter, [])
    const [option, setOption] = useState('context')

    return (
        <div className='flex flex-col m-auto items-center justify-center mt-36 w-full h-screen pb-10'>
            <div className='flex flex-row justify-evenly  w-1/4 '>
                <button
                    className={option === 'context' ? 'border-b-2 border-red-700 w-1/3 text-red-700 text-lg'
                        : ' w-1/3 text-black text-lg'}
                    onClick={() => setOption('context')}
                >
                    Context
                </button>
                <button
                    className={option === 'parameters' ? 'border-b-2 border-red-700 w-1/3 text-red-700 text-lg'
                        : ' w-1/3 text-black text-lg'}
                    onClick={() => setOption('parameters')}
                >
                    Parameters</button>
            </div>
            {
                option === 'context' ?
                    <ContextPage context={presenter.model.configuration?.context} />
                    :
                    <ParametersPage configuration={presenter.model.configuration}/>
            }
        </div>
    )
}
