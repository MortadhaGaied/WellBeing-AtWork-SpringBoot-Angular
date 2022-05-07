import { Component, OnInit } from '@angular/core';
import {Sujet} from "../../Models/Evaluation/Sujet";
import {VoteService} from "../Vote.service";
import {Router} from "@angular/router";
import {HttpClient} from "@angular/common/http";
import {FormBuilder,FormControl, FormGroup, Validators} from '@angular/forms';

@Component({
  selector: 'app-add-sujet',
  templateUrl: './add-sujet.component.html',
  styleUrls: ['./add-sujet.component.scss']
})
export class AddSujetComponent implements OnInit {
  sujetForm: FormGroup;


  sujet:Sujet=new Sujet();
  description:FormControl;

  constructor( private fb: FormBuilder,private VS:VoteService,private router:Router,private http:HttpClient) {}

  ngOnInit(): void {

    this.sujetForm = this.fb.group({
      nomSujet: ['', Validators.required],
      description: ['', Validators.required],
      dateAjout: ['', Validators.required],
    });

  }


  addSujet()
  {


 this.VS.addSujet(this.sujetForm.value).subscribe(()=>this.router.navigateByUrl("/evaluation/Vote"));
    console.log(this.sujet);


  }


  Cancel()
  { this.router.navigate(['/evaluation/evaluation'])
  }










}
