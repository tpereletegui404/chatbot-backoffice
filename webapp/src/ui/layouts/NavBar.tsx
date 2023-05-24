import { useEffect, useState } from 'react'
import Link from 'next/link'

export const NavBar = () => {
    const [nav,setNav] = useState(false)

    const handleNav = () => {
        setNav(!nav)
    }

    return <>
        <div className="absolute bg-white left-0 top-0 w-full z-10 ease-in duration-300">
            <div className="max-w=[1240px] m-auto flex justify-between items-center p-4 text-black">
                <Link href="/">
                    <h1 className="font-bold text-3xl text-black">CHAT<span className='text-red-700'>BOT</span></h1>
                </Link>
                <ul className="hidden sm:flex">
                    <li className="p-4">
                        <Link  className='hover:text-red-700' href="/">Home</Link>
                    </li>
                    <li className="p-4">
                        <Link className='hover:text-red-700' href="/settings">Settings</Link>
                    </li>
                    <li className="p-4">
                        <Link className='hover:text-red-700' href="/logout">Logout</Link>
                    </li>
                </ul>
                {/*Mobile*/}
                <div className="block sm:hidden z-10">
                    <h2 onClick={handleNav}>Menu</h2>
                </div>
                {/*Mobile menu*/}
                <div
                    className={nav ? 'sm:hidden absolute top-0 left-0 right-0 bottom-0 flex ' +
                        'justify-center items-center w-full h-screen bg-white text-center text-red-700 ease-in duration-300' :
                        'sm:hidden absolute top-0 left-[-100%] right-0 bottom-0 flex ' +
                        'justify-center items-center w-full h-screen bg-white text-center text-red-700 ease-in duration-300'
                    }>
                    <ul>
                        <li className='p-4 text-4xl hover:text-gray-500'>
                            <Link href="/">Home</Link>
                        </li>
                        <li className='p-4 text-4xl hover:text-gray-500'>
                            <Link href="/settings">Settings</Link>
                        </li>
                        <li className='p-4 text-4xl hover:text-gray-500'>
                            <Link href="/contact">Logout</Link>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
    </>
}