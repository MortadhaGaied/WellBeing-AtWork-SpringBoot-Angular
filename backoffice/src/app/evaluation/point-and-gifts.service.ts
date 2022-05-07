import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import { User } from './user';



@Injectable({
  providedIn: 'root'
})
export class PointAndGiftsService {

  constructor(private http:HttpClient) { }

  baseURL: string = 'http://localhost:8081/Wellbeignatwork/PointsAndGift/PointRanking';


PointsRanking(): Observable<User[]>
{

  return this.http.get<User[]>(this.baseURL);
}

saveGame: string='http://localhost:8081/Wellbeignatwork/PointsAndGift/saveGift'
save()
{
  
}





}
