
export class User {
    id: number | undefined | null;
    name: string | undefined;
    email: string | undefined | null;
    password: string | undefined;
    token: string | undefined;
    init: string | undefined;
    functionalities: Array<string> | undefined
}
