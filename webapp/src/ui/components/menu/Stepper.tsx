import { useState } from 'react'

interface Step {
    id: number,
    content: string,
    isHidden: boolean
}

export const Stepper = () => {
    const initialState = {
        step1: { id: 1, content: 'contenido del paso 1', isHidden: false },
        step2: { id: 2, content: 'contenido del paso 2', isHidden: true },
        step3: { id: 3, content: 'contenido del paso 3', isHidden: true }
    }
    const allHidden = {
        step1: { id: 1, content: 'contenido del paso 1', isHidden: true },
        step2: { id: 2, content: 'contenido del paso 2', isHidden: true },
        step3: { id: 3, content: 'contenido del paso 3', isHidden: true }
    }

    const [steps, setSteps] = useState<{step1: Step, step2: Step, step3: Step}>(initialState)

    const handleClickOnStep = (item) => {
        const selectedStep = `step${item.id}`
        if (steps[selectedStep].isHidden == false) return
        setSteps({...allHidden, [selectedStep]: { ...steps[selectedStep], isHidden: !steps[selectedStep].isHidden }})
    }
    return <>
        <div className="flex items-center justify-evenly">
            <div className="flex gap-10">
                {
                    Object.values(steps).map(item => <div className="flex items-center" key={item.id}>
                        <div className="relative flex items-center flex-col">
                            <div onClick={() => handleClickOnStep(item)} className="w-4 h-4 bg-blue-500 rounded-full cursor-pointer"></div>
                            Step {item.id}
                        </div>
                    </div>)
                }
            </div>
        </div>
        {
            Object.values(steps).map(step => <div className={`mt-4 ${step.isHidden && "hidden"}`} key={step.id}>
                    {step.content}
                </div>
            )
        }
    </>
}