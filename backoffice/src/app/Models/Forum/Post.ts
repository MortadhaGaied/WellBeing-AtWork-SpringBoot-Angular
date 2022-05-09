import { User } from "../User";

export class Post{
    content: string;
    createdAt: Date;
    file: string;
    id: number;
    modifiedAt: Date;
    subject: string;
    tags: string[];
    user:User;
    reported:boolean;
}