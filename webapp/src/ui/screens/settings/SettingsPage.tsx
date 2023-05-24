import {useState} from "react"
import {ContextPage} from "../context/ContextPage"

export const SettingsPage = () => {
    const [option, setOption] = useState('context')

    return (
        <div className='flex flex-col m-auto items-center justify-center mt-16 w-full h-screen pb-10'>
            <div className='flex flex-row justify-evenly  w-1/4 mt-52'>
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
                    <ContextPage/>
                    :
                    <ContextPage />
            }
        </div>
    )
}
