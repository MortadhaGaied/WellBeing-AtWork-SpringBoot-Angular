import { Component, OnInit , Inject, Input} from '@angular/core';
import { Router } from '@angular/router';
import { EventService } from '../../event/event.service';
import { Event } from '../../Models/Forum/Event/Event';
import { FileUpload } from '../../Models/file-upload.model';
import { ImageService } from '../../image.service';
import {COMMA, ENTER} from '@angular/cdk/keycodes';
import {MatChipInputEvent} from '@angular/material/chips';
import { MatDialogRef } from '@angular/material/dialog';
@Component({
  selector: 'app-add-event-component',
  templateUrl: './add-event-component.component.html',
  styleUrls: ['./add-event-component.component.scss']
})
export class AddEventComponentComponent implements OnInit {
  event : Event = new Event();
  url:string;
  selectedFiles?: FileList;
  currentFileUpload: FileUpload;
  percentage = 0;
  addOnBlur = true;
  readonly separatorKeysCodes = [ENTER, COMMA] as const;
  tags: string[] = ['SPORT', 'ANIMAL'];
  constructor(@Inject(EventService) private ev:EventService, 
  private _router:Router, private uploadService: ImageService,private dialogRef:MatDialogRef<AddEventComponentComponent> ) { }

  ngOnInit(): void {
    this.ev.$eventEmit.subscribe((data)=> {
      console.log("aaaaaaa");
      this.event=data;
      this.tags=[...this.event.eventTags];
    })

  }
  addEvent (){
    this.event.image=this.currentFileUpload.url;
    this.event.eventTags=this.tags;
    console.log(this.event);
    this.ev.addEvent(this.event).subscribe(
      ()=>{
        
        this.dialogRef.close();
        this._router.navigateByUrl("/event/events").then(()=>window.location.reload());
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

    // Add our tags
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


}
