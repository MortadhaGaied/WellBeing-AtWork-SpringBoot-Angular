import {Sujet} from "./Sujet";
import { User } from "../../evaluation/user";

export class VoteIdea {
  idVote:number;
  nbYes:number;
  nbNo: number;
  idUser:User;
  idSujet:Sujet;
}
export interface IVote {
  nbNo:number,
  nbYes:number
}
