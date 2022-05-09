import { Component, OnInit,Inject } from '@angular/core';
import { EventService } from '../event.service';
import { Event } from '../../Models/Forum/Event/Event';
import { FileUpload } from '../../Models/file-upload.model';
import { ImageService } from '../../image.service';
import { Router } from '@angular/router';
import { MatDialogRef } from '@angular/material/dialog';
import {COMMA, ENTER} from '@angular/cdk/keycodes';
import {MatChipInputEvent} from '@angular/material/chips';
@Component({
  selector: 'app-update-event',
  templateUrl: './update-event.component.html',
  styleUrls: ['./update-event.component.scss']
})
export class UpdateEventComponent implements OnInit {

  event:Event=new Event();
  url:string;
  selectedFiles?: FileList;
  currentFileUpload: FileUpload;
  percentage = 0;
  addOnBlur = true;
  readonly separatorKeysCodes = [ENTER, COMMA] as const;
  tags: string[] =[];
  
  constructor(@Inject(EventService) private ev:EventService,private _router:Router,
  private dialogRef:MatDialogRef<UpdateEventComponent>,private uploadService: ImageService) { }

  ngOnInit(): void {
    this.ev.$eventEmit.subscribe((data)=> {
      this.event=data;
      console.log(this.event);
      this.tags=[...this.event.eventTags];
    })
    
  }
  updateEvent (){
    this.event.image=this.currentFileUpload.url;
    console.log(this.currentFileUpload.key);
    this.event.eventTags=this.tags;
    console.log(this.event);
    this.ev.updateEvent(this.event).subscribe(()=>
    {
      console.log("modifier");
      this.dialogRef.close();
      this._router.navigateByUrl("/event/calendar").then(()=>window.location.reload());
    })
  }
  selectFile(event: any): void {
    this.selectedFiles = event.target.files;
   
    if (this.selectedFiles) {
      const file: File | null = this.selectedFiles.item(0);
      this.selectedFiles = undefined;
      if (file) {
        this.currentFileUpload = new FileUpload(file);
        this.uploadService.pushFileToStorage(this.currentFileUpload).subscribe(
          percentage => {
            this.percentage = Math.round(percentage ? percentage : 0);
            
          },
          error => {
            console.log(error);
          }
          
        );
      }
    }
  }
  add(event: MatChipInputEvent): void {
    const value = (event.value || '').trim();

    // Add our fruit
    if (value) {
      this.tags.push(value);
    }

    // Clear the input value
    event.chipInput!.clear();
  }

  remove(tag: string): void {
    const index = this.tags.indexOf(tag);

    if (index >= 0) {
      this.tags.splice(index, 1);
    }
  }
  deleteEvent(idEvent:number){
    this.ev.deleteEventById(idEvent)
    .subscribe(()=>this.ev.getAllEvent().subscribe(
      ()=>{
        this.dialogRef.close();
        this._router.navigateByUrl("/event/calendar").then(()=>window.location.reload());
      }
      ));
  }
}