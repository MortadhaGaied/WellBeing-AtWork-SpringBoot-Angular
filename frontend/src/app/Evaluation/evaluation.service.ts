import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import { UserGift } from './UserGift';
import { VoteIdea } from './VoteIdea';
import { Sujet } from './Sujet';




@Injectable({
  providedIn: 'root'
})
export class EvaluationService {

  constructor(private http:HttpClient) { }

  UserGift(idUser: number): Observable<UserGift> {
    return this.http
      .get<UserGift>(`http://localhost:8081/Wellbeignatwork/PointsAndGift/Gift/${idUser}`)

  }
  
  baseURL: string = 'http://localhost:8081/Wellbeignatwork/Vote/AffichageSujet';



  FindSujet(): Observable<Sujet[]>
  {
  return this.http.get<Sujet[]>(this.baseURL);
  }





  addYes(sujetId:number,idUser:number,vote:VoteIdea)
  {
    return this.http
    .post(`http://localhost:8081/Wellbeignatwork/Vote/ajouterPour/${sujetId}/${idUser}`,vote);

  }












}
