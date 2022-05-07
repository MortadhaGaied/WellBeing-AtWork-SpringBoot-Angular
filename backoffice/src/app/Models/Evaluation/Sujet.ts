


import { User } from "../../evaluation/user";
import {VoteIdea} from "./VoteIdea";

export class Sujet
{
  id : number;
  nomSujet:string;
  description:string;
  dateAjout:Date;
  imageUrl:string;
  nbYes:number;
  nbNo:number;
  nbpoint:number;
  idUser:User;
  votesSujet:VoteIdea;


}
