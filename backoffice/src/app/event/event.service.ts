import { EventEmitter, Injectable, Output } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { InteropObservable, Observable } from 'rxjs';
import { Event } from '../Models/Forum/Event/Event';
import { take } from 'rxjs/operators';
@Injectable({
  providedIn: 'root'
})
export class EventService {

  event : Event;
  $eventEmit = new EventEmitter();
  constructor(private _http:HttpClient) { }
  getAllEvent():Observable <Event[]>{
    return this._http.get<Event[]>("http://localhost:8081/Wellbeignatwork/event/getAllEvents");
  }
  
  deleteEventById(idEvent:number){
    return this._http.delete("http://localhost:8081/Wellbeignatwork/event/removeE/"+idEvent);
  }

  addEvent(event:Event){
     return this._http.post<Event>("http://localhost:8081/Wellbeignatwork/event/AddE/3",event);
}

  updateEvent(event:Event ){
  return this._http.put<Event>("http://localhost:8081/Wellbeignatwork/event/modifyE",event);
}

getEventById(idEvent: number): Observable<Event> {
  return this._http.get<Event>("http://localhost:8081/Wellbeignatwork/event/getEventById/"+idEvent);
 }

  sendEventData(idEvent : number){
    console.log("event service");
    this.getEventById(idEvent).pipe(take(1)).subscribe(x=>{
      console.log(x.eventName);
      this.event=x;
      this.$eventEmit.emit(this.event);
    });
  }

 

    /*
    assignUserToEvent (idEvent:number, idUser:number) {
    return this._http.get("http://localhost:8081/Wellbeignatwork/event/assign-user-to-event/"+idEvent,+idUser);}
    */
  
}

