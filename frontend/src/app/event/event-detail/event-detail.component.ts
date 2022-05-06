import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Event } from '../../Models/event/Event';
import { EventService } from '../event-list/event.service';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';

 

@Component({
  selector: 'app-event-detail',
  templateUrl: './event-detail.component.html',
  styleUrls: ['./event-detail.component.css']
})
export class EventDetailComponent implements OnInit {
  title ="google-maps"
  starRating = 0; 
  rating3:number;
  public form: FormGroup;
  nbrMax:number;
  poursentage :number;
  nbr :number;
  event : Event = new Event();
  idEvent : number;
  idUser :any;
  constructor(private route:ActivatedRoute,private _service:EventService,
    private router:Router,private fb: FormBuilder) { this.rating3 = 0;
      this.form = this.fb.group({
        rating: ['', Validators.required],
      })}
    
  ngOnInit(): void {
    this.idEvent=this.route.snapshot.params['id'];
    this._service.getEventById(this.idEvent).subscribe((e)=>{
    this.event=e;
    this.nbrMax=e.nbrMaxParticipant;
    console.log(this.event);
  });
  this._service.getNbrParticipantByEvent(this.idEvent).subscribe((e)=>{
    this.nbr=e;
    this.poursentage=e/this.nbrMax;
    console.log(this.poursentage);
    console.log(this.nbr);
  });

  this.idUser = localStorage.getItem('idUser');
  }
  join(){
    this._service.assignUserToEvent(this.idEvent,this.idUser).subscribe((e)=>{
            console.log(e);
            const blob=new Blob([e],{type:'application/pdf'});
            if(window.navigator && (window.navigator as any).msSaveOrOpenBlob){
              (window.navigator as any).msSaveOrOpenBlob(blob);
              return;
            }
            const data =window.URL.createObjectURL(blob);
            const link =document.createElement('a');
            link.href=data;
            link.download='ticket.pdf';
            link.dispatchEvent(new MouseEvent('click',{bubbles:true,cancelable:true,view:window}));
            setTimeout(function(){
              window.URL.revokeObjectURL(data);
              link.remove();
            },100);
          });
    
  
    this.router.navigateByUrl("/event-list")

  }


}
