import axios from 'axios'

export function ledgerHttp() {
    const baseUrl = process.env['LEDGER_BASE_URL']
    let headers = {
        'Content-Type': 'application/json',
    }
    const accessToken = process.env['LEDGER_ACCESS_TOKEN']
    if (accessToken) {
        headers['Authorization'] = accessToken
    }
    return axios.create({ baseURL: baseUrl, headers });
}
