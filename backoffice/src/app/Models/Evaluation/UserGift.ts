import { User } from "app/evaluation/user";


export interface UserGift{
  id:number,
  code:string,
  montant:number,
  idUser:User[],
}
