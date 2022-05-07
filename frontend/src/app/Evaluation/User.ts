import { Badge } from "./badge";
import { UserGift } from "./UserGift";

export interface User
{
  id:number,
  firstName:string,
  lastName:string,
  pointFidelite:number,
  CadeauUser:UserGift,
  badge:Badge
}
