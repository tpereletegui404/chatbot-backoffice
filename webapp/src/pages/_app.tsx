import 'asimov-javascript-extensions'
import '../ui/layouts/styles/components-theme.css'
import '../ui/layouts/styles/globals.css'
import type { AppProps } from 'next/app'
import { defaultWebAppConfig, WebApp } from '../ui/WebApp'

const application = new WebApp(defaultWebAppConfig())

const Application = ({ Component, pageProps }: AppProps) => application.render(Component, pageProps)

// Disable Next.JS static generation
Application.getInitialProps = async (ctx) => ({})

export default Application
