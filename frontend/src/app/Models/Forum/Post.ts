import { Reaction } from "./Reaction";
import { Comment } from "./Comment";
import { User } from "../User";
export class Post{
    content: string;
    createdAt: Date;
    file: string;
    id: number;
    modifiedAt: Date;
    subject: string;
    tags: string[];
    reactions:Reaction[];
    comments:Comment[];
    user:User;
    reported:boolean;
}