import chalk from 'chalk'
import path from 'path'
import glob from 'glob'
require('dotenv').config({ path: path.join(__dirname, '../.env') })

export class Console {
    private commands = {}
    private args = process.argv.slice(2) // Slice node executable and console

    constructor() {
        this.registerAllCommands()
    }

    async start() {
        process.chdir('../../')
        this.checkArgumentsCount()
        try {
            await this.processCommand()
        } catch(e) {
            console.error(e)
        }
    }

    private checkArgumentsCount() {
        if (this.args.length === 0) {
            this.showUsage()
        }
    }

    private registerAllCommands() {
        const files = glob.sync(path.join(__dirname, 'commands/**/*.ts'))
        files.forEach((file) => {
            this.registerCommand(require(file))
        })
    }

    private registerCommand(commandClass) {
        const commandInstance = new (commandClass.default)()
        this.commands[commandInstance.name] = commandInstance
        if (commandInstance.alias) {
            this.commands[commandInstance.alias] = commandInstance
        }
    }

    private showUsage() {
        let usageText = `
  Development console.

  ${chalk.green.bold('Usage:')} console <command> <options>

  ${chalk.green.bold('All commands:')}

`
        const maxCommandName = this.maxCommandNameLength()
        const commands = Object.keys(this.commands).sort()
        for (const command of commands) {
            usageText += this.commandUsageText(command, maxCommandName)
        }

        usageText += `
        
  ${chalk.green.bold('Common commands:')}
        
`

        for (const command of commands) {
            const commandInstance = this.commands[command]
            if (!commandInstance.isCommon) continue
            if (commandInstance.alias && command !== commandInstance.alias) continue
            usageText += this.commandUsageText(command, maxCommandName)
        }

        console.log(usageText)
        process.exit(1)
    }

    private commandUsageText(command, maxCommandName) {
        const usage = this.commandUsage(command)
        const description = this.commands[command].description
        return '    console ' + usage.padEnd(maxCommandName + 1, ' ') + '        ' + description + '\n'
    }

    private commandUsage(command) {
        const parameters = this.commands[command].parametersUsage ?? ''
        return command + ' ' + parameters
    }

    private maxCommandNameLength() {
        let maxCommandName = 0
        for (const command of Object.keys(this.commands)) {
            const usage = this.commandUsage(command)
            if (usage.length > maxCommandName) {
                maxCommandName = usage.length
            }
        }

        return maxCommandName
    }

    private async processCommand() {
        for (const commandName of Object.keys(this.commands)) {
            if (this.commandMatches(commandName, this.args)) {
                const commandNamePartCount = commandName.split(' ').length
                const commandArgs = this.args.slice(commandNamePartCount)
                await this.commands[commandName].execute(commandArgs, this.showUsage.bind(this))
                return
            }
        }

        console.log('Unrecognized command: ' + this.args[0])
        this.showUsage()
    }

    private commandMatches(commandName, args) {
        const commandNameParts = commandName.split(' ')
        if (args.length < commandNameParts.length) {
            return false
        }

        for(let i = 0; i < commandNameParts.length; i++) {
            if (commandNameParts[i] !== args[i]) {
                return false
            }
        }

        return true
    }
}

(new Console()).start()
