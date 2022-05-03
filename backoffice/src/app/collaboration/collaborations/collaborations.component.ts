import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Collaboration } from '../../Models/Collaboration/collaboration';
import { CollaborationService } from '../../Service/collaboration.service';

@Component({
  selector: 'app-collaborations',
  templateUrl: './collaborations.component.html',
  styleUrls: ['./collaborations.component.scss']
})
export class CollaborationsComponent implements OnInit {
  
  c : Collaboration[];
  constructor(private collaborationService: CollaborationService,private router : Router ) { }

  ngOnInit(): void {
    this.collaborationService.getAllColaboration().subscribe(
      (data)=>{ 
        console.log(data);
        this.c=data
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

      viewCollaboration(idCollaboration:number){
        let collaboration;
        for (let i=0;i<this.c.length;i++) {
          if (this.c[i].idCollaboration == idCollaboration){
            collaboration= this.c[i];
          }
          this.router.navigate([`viewCollaboration/${idCollaboration}`])
        }
      }
}
