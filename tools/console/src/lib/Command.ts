export interface Command {
    name: string
    description: string
    alias?: string
    isCommon?: boolean
    execute(args?:string[], showUsage?: Function): Promise<void>
}
