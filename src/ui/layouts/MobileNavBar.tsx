import { MenuItem } from '@/ui/components/menu/MenuItem'
import { FC } from 'react'

const routes = [
    { path: '/', name: 'Home' },
    { path: '/about', name: 'About' },
    { path: '/services', name: 'Services' },
    { path: '/pricing', name: 'Pricing' },
    { path: '/contact', name: 'Contact' },
]

export const MobileNavBar:FC<{ isVisible: boolean }> = ({ isVisible }) => {
    return <div className={`${isVisible ? 'block' : 'hidden'} w-full md:block md:w-auto`} id="navbar-default">
        <ul className="font-medium flex flex-col p-4 md:p-0 mt-4 border border-gray-100 rounded-lg bg-gray-50 md:flex-row md:space-x-8 md:mt-0 md:border-0 md:bg-white dark:bg-gray-800 md:dark:bg-gray-900 dark:border-gray-700">
            {
                routes.map(i => <MenuItem path={i.path} name={i.name} key={i.name}/>)
            }
        </ul>
    </div>
}