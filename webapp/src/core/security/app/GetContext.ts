import {PureCommand} from "asimov-cqbus/dist/requests/PureCommand"

export class CreateContext extends PureCommand {
    constructor(public context: string) { super() }
}
