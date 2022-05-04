import { Collaboration } from './../../Models/Collaboration/collaboration';
import { ActivatedRoute, Router } from '@angular/router';
import { Component, Inject, Injectable, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { CollaborationService } from '../../Service/collaboration.service';

@Component({
  selector: 'app-update-collaboration',
  templateUrl: './update-collaboration.component.html',
  styleUrls: ['./update-collaboration.component.scss']
})
export class UpdateCollaborationComponent implements OnInit {

file:File;
Collaboration : Collaboration = new Collaboration;
  title: any;
  button: any;
  idCollaboration: any;
  formCollaboration: FormGroup;

  constructor(private formBuilder: FormBuilder, private activatedRoute: ActivatedRoute, router: Router,@Inject(CollaborationService)
    private collaborationService: CollaborationService) { }

  ngOnInit(): void {
    this.collaborationService.$eventEmit.subscribe((data)=> {
      this.Collaboration=data;
      console.log(this.Collaboration);
    })
  }
  onFileSelected(event:any){
    this.file=event.target.files[0];
    console.log(this.file)
  }
  updateCollaboration(){
    console.log(this.file);
    console.log(this.Collaboration)
    this.Collaboration.imagesCollab=undefined;
    const formdata=new FormData();
  formdata.append('image',this.file,this.file.name);
console.log(formdata.get('image'))
    this.collaborationService.updateCollaboration(this.Collaboration).subscribe(collab =>
       {console.log(collab)
         this.collaborationService.uploadImageToCollabotration(formdata,JSON.parse(JSON.stringify(collab)).idCollaboration).subscribe(data => window.alert("image uploaded successfully"),(error)=>console.log(error))})

  }
}


