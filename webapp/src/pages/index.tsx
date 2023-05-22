import Link from 'next/link'

export default () => (<>
    <main>
        <section>
            <div>
                <p>asd</p>
            </div>
        </section>
        <div className='text-center'>
            <button className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded">
                <Link href={"/onboarding"}> Start Onboarding!</Link>
            </button>
        </div>
    </main>
</>)
