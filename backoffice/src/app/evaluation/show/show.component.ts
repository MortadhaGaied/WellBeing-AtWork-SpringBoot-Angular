import {Component, EventEmitter, Inject, OnInit, Output} from '@angular/core';
import {Sujet} from "../../Models/Evaluation/Sujet";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {VoteService} from "../Vote.service";
import {ActivatedRoute, Router} from "@angular/router";
import {HttpClient} from "@angular/common/http";
import {MatDialogRef, MAT_DIALOG_DATA, MatDialog} from '@angular/material/dialog';


@Component({
  selector: 'app-show',
  templateUrl: './show.component.html',
  styleUrls: ['./show.component.scss']
})
export class ShowComponent implements OnInit {
  sujet:Sujet=new Sujet();
  sujetForm: FormGroup;
  constructor( private VS:VoteService,private router:Router,private http:HttpClient, public dialogRef: MatDialogRef<ShowComponent>,
               @Inject(MAT_DIALOG_DATA) public data: Sujet,private route: ActivatedRoute,public dialog: MatDialog,
               private fb: FormBuilder) {}
  ngOnInit(): void {

    this.sujetForm = this.fb.group({
      nomSujet: ['', Validators.required],
      description: ['', Validators.required],
      dateAjout: ['', Validators.required],
    });
    console.log(this.data);
    this.showTask();
  }


  showTask() {
    this.VS.getSujetById(this.data.id).subscribe(
      (d: Sujet) => {
        console.log(d);
        this.sujetForm.controls['nomSujet'].setValue(d.nomSujet);
        this.sujetForm.controls['dateAjout'].setValue(
          new Date(d.dateAjout).toISOString()
        );
        this.sujetForm.controls['description'].setValue(d.description);
      },
      (error) => console.error(error)
    );
  }



  updateSujet()
  {
    this.VS.updateTask(this.sujetForm.value, this.data.id).subscribe(
      (d) => {
        this.dialogRef.close();
      },
      (error) => console.error(error)
    );

  }




























}
