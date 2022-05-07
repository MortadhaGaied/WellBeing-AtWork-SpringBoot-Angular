import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Survey} from "../Models/Evaluation/Survey";

@Injectable({
  providedIn: 'root'
})
export class QVTEvaluationService {

 baseURL: string = 'http://localhost:8081/Wellbeignatwork/QVT/Survey';


  constructor(private http:HttpClient) { }

  SendSurvey(): Observable<Survey[]>
  {
    return this.http.get<Survey[]>(this.baseURL);
  }








}
