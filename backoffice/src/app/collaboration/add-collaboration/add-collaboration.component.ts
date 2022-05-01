import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Collaboration } from '../../Models/Collaboration/collaboration';
import { CollaborationService } from '../../Service/collaboration.service';





@Component({
  selector: 'app-add-collaboration',
  templateUrl: './add-collaboration.component.html',
  styleUrls: ['./add-collaboration.component.scss']
})
export class AddCollaborationComponent implements OnInit {


  idUser: any;
  collaboration: Collaboration = {
    date: new Date(),
    description: '',
    email: '',
    name: '',
    idCollaboration: 0,
    phone: 0,
    picture: {
      id: 0,
      name: ''
    },
    town: '',
  }
file:File;
  c: Collaboration[];

  title: any;
  button: any;
  idCollaboration: any;
  formCollaboration: FormGroup;

  constructor(private formBuilder: FormBuilder, private activatedRoute: ActivatedRoute, router: Router,
    private collaborationService: CollaborationService) { }

  ngOnInit(): void {
  }
  addCollaboration(){
    console.log(this.file);
    this.collaborationService.addCollaboration(this.collaboration).subscribe(collab => this.collaborationService.uploadImageToCollabotration(this.file,JSON.parse(JSON.stringify(collab)).idCollaboration).subscribe(data => console.log(data),(error)=>console.log(error)))
   
  }
}
