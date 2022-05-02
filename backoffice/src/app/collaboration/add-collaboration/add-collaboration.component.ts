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
  onFileSelected(event:any){
    this.file=event.target.files[0];
    console.log(this.file)
  }
  addCollaboration(){
    console.log(this.file);
    const formdata=new FormData();
  formdata.append('image',this.file,this.file.name);

    this.collaborationService.addCollaboration(this.collaboration).subscribe(collab => this.collaborationService.uploadImageToCollabotration(formdata,JSON.parse(JSON.stringify(collab)).idCollaboration).subscribe(data => window.alert("image uploaded successfully"),(error)=>console.log(error)))

  }
}