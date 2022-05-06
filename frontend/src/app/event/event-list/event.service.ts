import { EventEmitter, Injectable, Output } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { InteropObservable, Observable } from 'rxjs';
import { Event } from '../../Models/event/Event';
import { take } from 'rxjs/operators';
import { Weather } from '../../Models/event/Weather';
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
assignUserToEvent(idEvent:number,idUser:number):Observable<Blob>{
  return this._http.get("http://localhost:8081/Wellbeignatwork/event/assign-user-to-event/"+idUser +"/"+idEvent,{responseType:'blob'});

}
getAllEventByDistance():Observable <Event[]>{
  return this._http.get<Event[]>("http://localhost:8081/Wellbeignatwork/event/sortByDistance");
}
getAllEventByFrais():Observable <Event[]>{
  return this._http.get<Event[]>("http://localhost:8081/Wellbeignatwork/event/sortByFrais");
}
getPopularEvent(): Observable<Event> {
  return this._http.get<Event>("http://localhost:8081/Wellbeignatwork/event/popularEvent");
 }

 getEventByUser(idUser:number):Observable <Event[]>{
  return this._http.get<Event[]>("http://localhost:8081/Wellbeignatwork/event/compare/"+idUser);
}

getNbrParticipantByEvent(idEvent:number):Observable <number>{
  return this._http.get<number>("http://localhost:8081/Wellbeignatwork/event/nbrByEvent/"+idEvent);
}
getEventTags(tag:string): Observable<Event[]> {
  return this._http.get<Event[]>("http://localhost:8081/Wellbeignatwork/event/EventTag/"+tag);
 }
 
 getWeather(idEvent:number) : Observable <Weather> {
  let params = new HttpParams();
  params=params.set('eventId', idEvent);
  return this._http.get<Weather>("http://localhost:8081/Wellbeignatwork/event/weather/",{params: params});
 }

}
