import { User } from "./User";

export interface UserGift
{
  id:number,
  code:string,
  validite:boolean,
  montant:number,
  idUser:User
}
