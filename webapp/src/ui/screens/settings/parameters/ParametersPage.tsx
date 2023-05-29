import { WebAppServices } from '../../../WebApp'
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
            <div className='flex flex-wrap m-auto items-center justify-between w-[400px] h-[300px] pl-2'>
                <div className='flex flex-col w-[150px]'>
                    <label className='text-center text-bold mb-5'>Apikey</label>
                    <input type={'text'}
                           className="h-[40px] rounded outlined-none"
                           onChange={(e) => presenter.setApiKey(e.target.value)}
                           value={chatbotConfig?.apiKey}/>
                </div>
                <div className='flex flex-col  w-[150px]'>
                    <label className='text-center text-bold mb-5'>Temperature</label>
                    <input type={'text'}
                           className="h-[40px] rounded outlined-none"
                           onChange={(e) => presenter.setTemperature(e.target.value)}
                           value={chatbotConfig?.temperature}/>
                </div>
                <div className='flex flex-col w-[150px]'>
                    <label className='text-center text-bold mb-5'>TopP</label>
                    <input type={'text'}
                           className="h-[40px] rounded outlined-none"
                           onChange={(e) => presenter.setTopP(e.target.value)}
                           value={chatbotConfig?.topP}/>
                </div>
                <div className='flex flex-col w-[150px]'>
                    <label className='text-center text-bold mb-5'>Frequency Penalty</label>
                    <input type={'text'}
                           className="h-[40px] rounded outlined-none"
                           onChange={(e) => presenter.setFrequencyPenalty(e.target.value)}
                           value={chatbotConfig?.frequencyPenalty}/>
                </div>
                <div className='flex flex-col w-[150px]'>
                    <label className='text-center text-bold mb-5'>Max Tokens</label>
                    <input type={'text'}
                           className="h-[40px] rounded outlined-none"
                           onChange={(e) => presenter.setMaxTokens(e.target.value)}
                           value={chatbotConfig?.maxTokens}/>
                </div>
                <div className='flex flex-col w-[150px]'>
                    <label className='text-center text-bold mb-5'>Presence penalty</label>
                    <input type={'text'}
                           className="h-[40px] rounded outlined-none"
                           onChange={(e) => presenter.setParameterPresencePenalty(e.target.value)}
                           value={chatbotConfig?.parameterPresencePenalty}/>
                </div>
            </div>
            <button className='block w-[200px] bg-red-700 text-white mt-10 h-10 rounded'
                    onClick={presenter.updateConfig}>Update
            </button>
        </div>
)
}

interface Props {
    configuration: Configuration
}