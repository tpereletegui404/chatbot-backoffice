import { WebAppServices } from "../../../WebApp"
import { ContextPresenter } from "./ContextPresenter"
import { useAppPresenter } from "../../../components/hooks/useAppPresenter"

const contextPresenter = (onChange, services: WebAppServices) =>
    new ContextPresenter(onChange, services.core)

export const ContextPage = ({context}: Props) => {
    const presenter = useAppPresenter(contextPresenter, [context])

    return (
        <div className='flex flex-col m-auto items-center justify-center w-full mt-8 pb-10'>
            <textarea
                   className='block mx-auto w-[600px] h-[400px] text-left outline-none pl-2 pt-2'
                   value={presenter.model.context}
                   onChange={(e) => presenter.setContext(e.target.value)}
                   style={{ verticalAlign: 'top' }}
            />
            <button className='block w-[200px] bg-red-700 text-white mt-10 h-10 rounded'
                    onClick={presenter.updateContext}>Update
            </button>
        </div>
    )
}

interface Props {
    context: string
}