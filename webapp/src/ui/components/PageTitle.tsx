import React from 'react'
import Head from 'next/head'

export const PageTitle: React.FC<{ title?: string }> = (props) => {
    const baseTitle = 'Chatbot'

    return <Head>
        <title>{props.title ? `${baseTitle} // ${props.title}` : baseTitle}</title>
    </Head>
}
