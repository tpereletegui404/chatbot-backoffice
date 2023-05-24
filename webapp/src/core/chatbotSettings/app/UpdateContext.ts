import {PureCommand} from "asimov-cqbus/dist/requests/PureCommand"

export class UpdateContext extends PureCommand {
    constructor(public context: string) { super() }
}
