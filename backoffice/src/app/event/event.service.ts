import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { InteropObservable, Observable } from 'rxjs';
import { Event } from '../Models/Forum/Event/Event';
@Injectable({
  providedIn: 'root'
})
export class EventService {

  constructor(private _http:HttpClient) { }
  getAllEvent():Observable <Event[]>{
    return this._http.get<Event[]>("http://localhost:8081/Wellbeignatwork/event/getAllEvents");
  }
  
  deleteEventById(idEvent:number){
    return this._http.delete("http://localhost:8081/Wellbeignatwork/event/removeE/"+idEvent);
  }

  addEvent(event:Event){
     return this._http.post<Event>("http://localhost:8081/Wellbeignatwork/event/AddE",event);
}


}
