import { Post } from './../Models/Forum/Post';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Collaboration } from '../Models/Collaboration/collaboration';

@Injectable({
  providedIn: 'root'
})
export class CollaborationService {

  constructor(private http : HttpClient) { }

  getAllColaboration() :Observable<Collaboration[]>{
    return this.http.get<Collaboration[]>("http://localhost:8081/Collaboration/Collaboration/retrieveAllCollaborations")
  }
  getCollaborationById( idCollaboration : number){
    return this.http.get("http://localhost:8081/Collaboration/Collaboration/retrieveCollaboration/"+idCollaboration)
  }
  updateCollaboration(Collaboration: Collaboration){
    return this.http.put("http://localhost:8081/Collaboration/Collaboration/updateCollaboration/",Collaboration)
  }

  addCollaboration(Collaboration :Collaboration){
    return this.http.post("http://localhost:8081/Wellbeignatwork/Collaboration/addCollaboration/",Collaboration)
  }

  deleteCollaboration(idCollaboration: number){
    return this.http.delete("http://localhost:8081/Collaboration/Collaboration/deleteCollaboration/"+idCollaboration)
  }
  uploadImageToCollabotration(form:FormData , idCollaboration : number ){

 return this.http.post("http://localhost:8081/Wellbeignatwork/Collaboration/uploadImageToCollabotration/"+idCollaboration, form)
  }
}
