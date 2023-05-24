import {useRouter} from "next/router"

export const HomePage = () => {
    const router = useRouter()

    return (
        <div className='flex items-center justify-center w-full h-screen'>
            <button onClick={() => router.push('/settings')} className='block w-[200px] bg-red-700 text-white mt-10 h-10 rounded'>Settings</button>
        </div>
    )
}