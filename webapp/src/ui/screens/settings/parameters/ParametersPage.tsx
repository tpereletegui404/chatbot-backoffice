import { WebAppServices } from '../../../WebApp'
import { ContextPresenter } from '../context/ContextPresenter'
import { useAppPresenter } from '../../../components/hooks/useAppPresenter'
import { Configuration } from '../../../../core/chatbotSettings/model/Configuration'
import { ParametersPresenter } from './ParametersPresenter'

const parametersPresenter = (onChange, services: WebAppServices) =>
    new ParametersPresenter(onChange, services.core)

export const ParametersPage = ({configuration}: Props) => {
    const presenter = useAppPresenter(parametersPresenter, [configuration])
    const chatbotConfig = presenter.model.configuration

    return (
        <div className='flex flex-col m-auto items-center justify-center  pb-10'>
            <div className='flex flex-wrap m-auto items-center justify-evenly w-[600px] h-[300px] pl-2'>
                <div className='flex flex-col w-[200px]'>
                    <label className='text-center text-bold mb-5'>Apikey</label>
                    <input type={'text'} className='h-[40px] rounded outlined-none' value={chatbotConfig?.apikey}/>
                </div>
                <div className='flex flex-col  w-[200px]'>
                    <label className='text-center text-bold mb-5'>Temperature</label>
                    <input type={'text'} className='h-[40px] rounded outlined-none' value={chatbotConfig?.temperature}/>
                </div>
                <div className='flex flex-col w-[200px]'>
                    <label className='text-center text-bold mb-5'>TopP</label>
                    <input type={'text'} className='h-[40px] rounded outlined-none' value={chatbotConfig?.topP}/>
                </div>
                <div className='flex flex-col w-[200px]'>
                    <label className='text-center text-bold mb-5'>Frequency Penalty</label>
                    <input type={'text'} className='h-[40px] rounded outlined-none' value={chatbotConfig?.frequencyPenalty}/>
                </div>
            </div>
        </div>
)
}

interface Props {
    configuration: Configuration
}