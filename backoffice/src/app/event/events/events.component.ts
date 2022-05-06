import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { Router } from '@angular/router';
import { Event } from '../../Models/Forum/Event/Event';
import { EventService } from '../event.service';
import { MatDialog } from '@angular/material/dialog'
import { AddEventComponentComponent } from '../add-event-component/add-event-component.component';
import { CalendarOptions } from '@fullcalendar/angular';
import { HttpClient } from '@angular/common/http';
import { UpdateEventComponent } from '../update-event/update-event.component';
@Component({
  selector: 'app-events',
  templateUrl: './events.component.html',
  styleUrls: ['./events.component.scss']
})
export class EventsComponent implements OnInit {
  search:string;
  Events :any= [];
  calendarOptions: CalendarOptions;
  constructor(private _service:EventService , 
    private router:Router,private matDialog:MatDialog,private httpClient: HttpClient) { }
  
  listEvent : Event[];
  updateId : number;
  @Output () eventEmmit= new EventEmitter(); 
  onDateClick(res:any) {
    alert('Clicked on date : ' + res.dateStr)
  }
  ngOnInit(): void {

    this._service.getAllEvent().subscribe(res=>{console.log(res);
      this.listEvent=res});
  
  }

  deleteEvent(idEvent:number){
    this._service.deleteEventById(idEvent)
    .subscribe(()=>this._service.getAllEvent().subscribe(res=>{this.listEvent=res}));
  }

  updateEvent(idEvent:number){
    this._service.sendEventData(idEvent);
    this.matDialog.open(UpdateEventComponent);
  }
  onOpenDialogClick(){
    this.matDialog.open(AddEventComponentComponent);
    
  }

  Search(){
    if(this.search!=""){
      this.listEvent=this.listEvent.filter(res=>{
        if(res.eventName.toLocaleLowerCase().match(this.search.toLocaleLowerCase())){
          return true; }
        else if(res.eventSeason.toLocaleLowerCase().match(this.search.toLocaleLowerCase())){
          return true; }
        else if(res.eventLocalisation.toLocaleLowerCase().match(this.search.toLocaleLowerCase())){
          return true; }
        else if(res.frais.toString().toLocaleLowerCase().match(this.search.toLocaleLowerCase())){
          return true; }
        else if(res.nbrMaxParticipant.toString().toLocaleLowerCase().match(this.search.toLocaleLowerCase())){
          return true;   }
        else if(res.revenue.toString().toLocaleLowerCase().match(this.search.toLocaleLowerCase())){
          return true; }

        else{
          return false;}

      });
    }
    else{
      this._service.getAllEvent().subscribe
      (res=>
        {
          console.log(res);
        this.listEvent=res
        }
      );
    }
  }
  cadeau(){
    this._service.getCadeau().subscribe(e=>console.log(e));
  }

   
  }




















