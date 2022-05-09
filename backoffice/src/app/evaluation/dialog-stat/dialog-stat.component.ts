import {Component, Inject, OnInit} from '@angular/core';
import {VoteService} from "../Vote.service";
import {ActivatedRoute, Router} from "@angular/router";
import {HttpClient} from "@angular/common/http";
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from "@angular/material/dialog";
import {Sujet} from "../../Models/Evaluation/Sujet";
import {FormBuilder} from "@angular/forms";

@Component({
  selector: 'app-dialog-stat',
  templateUrl: './dialog-stat.component.html',
  styleUrls: ['./dialog-stat.component.scss']
})
export class DialogStatComponent implements OnInit {
  sujets:Array<Sujet> ;

  name: any;
  sujet:any;
  sujetId: number;
  value:any;
  constructor(
    private VS:VoteService,private router:Router
    ,private http:HttpClient,
    public dialogRef: MatDialogRef<DialogStatComponent>,
               @Inject(MAT_DIALOG_DATA) public data: Sujet,private route: ActivatedRoute,public dialog: MatDialog,
               private fb: FormBuilder) {}

  ngOnInit(): void {




  }



}
