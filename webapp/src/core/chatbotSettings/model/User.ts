export class User {
    constructor(
        public id: number,
        public username: string,
    ) {}

    static fromJson(json: any): User {
        return new User(json.id, json.username)
    }
}
