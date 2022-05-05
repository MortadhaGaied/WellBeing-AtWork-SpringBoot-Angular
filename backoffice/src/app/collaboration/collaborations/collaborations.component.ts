import { ShowCollaborationComponent } from './../show-collaboration/show-collaboration.component';
import { Collaboration } from './../../Models/Collaboration/collaboration';
import { Post } from './../../Models/Forum/Post';
import { Component, EventEmitter, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CollaborationService } from '../../Service/collaboration.service';
import {NgxPaginationModule} from 'ngx-pagination';
import { UpdateCollaborationComponent } from '../update-collaboration/update-collaboration.component';
import {MatDialog, MAT_DIALOG_DATA} from '@angular/material/dialog';
@Component({
  selector: 'app-collaborations',
  templateUrl: './collaborations.component.html',
  styleUrls: ['./collaborations.component.scss']
})
export class CollaborationsComponent implements OnInit {
  c : Collaboration[];
  Collaboration : Collaboration;
  $eventEmit = new EventEmitter();
  constructor(private collaborationService: CollaborationService,private matDialog:MatDialog,private router : Router) { }

  totalLentgh:any;
  page:number = 1;
  ngOnInit(): void {
    this.collaborationService.getAllColaboration().subscribe(
      (data)=>{ 
        console.log(data);
        this.c=data
        this.totalLentgh = data.length 
      });
  }

  deleteCollabora(idCollaboration:number){
    this.collaborationService.deleteCollaboration(idCollaboration).subscribe(()=>
     this.collaborationService.getAllColaboration().
      subscribe(data=>{this.c}));
      }


      editCollaboration(idCollaboration:number){
        let collaboration;
        for (let i=0;i<this.c.length;i++) {
          if (this.c[i].idCollaboration == idCollaboration){
            collaboration= this.c[i];
          }
          this.router.navigate([`editCollaboration/${idCollaboration}`])
        }
      }


      deleteCollaboration(idCollaboration:number){
        this.collaborationService.deleteCollaboration(idCollaboration)
        .subscribe(()=>this.collaborationService.getAllColaboration().subscribe(res=>{this.c=res}));
      }

      updateCollaboration(idCollaboration:number){
    
        this.Collaboration=this.collaborationService.sendEventData(idCollaboration);
        this.matDialog.open(UpdateCollaborationComponent);
        }

        showCollaboration(collaboration:Collaboration){
  
          this.matDialog.open(ShowCollaborationComponent ,{data :collaboration});
          }
}
