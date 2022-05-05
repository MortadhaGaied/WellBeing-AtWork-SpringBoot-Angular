import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';



@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http : HttpClient) { }


  getCollaborationById( id : number){
    return this.http.get("http://localhost:8081/Wellbeignatwork/api/"+id)
  }
  }


