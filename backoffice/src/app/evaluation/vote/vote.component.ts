import {Component,EventEmitter, ViewChild,OnInit, Output } from '@angular/core';
import {Sujet} from "../../Models/Evaluation/Sujet";
import {VoteService} from "../Vote.service";
import {Router} from "@angular/router";
import { MatDialog } from '@angular/material/dialog';
import {ShowComponent} from "../show/show.component";



@Component({
  selector: 'app-vote',
  templateUrl: './vote.component.html',

  styles:[

    `
      .center-block { color:#003300 !important; }
      .bold { font-weight:bold; }
      .thumbnail { min-height: 210px; padding-left: 10px; background-color:#bbb; margin-bottom:10px; }
      .pad-left { margin-left: 10px; }
      .well div { color: #343a40; }`
  ]
})
export class VoteComponent implements OnInit {

nomSujet:any;


p:number=1;




 sujets: Sujet[]=[];

  @Output() refreshEmitter = new EventEmitter<boolean>();
  constructor(private VS:VoteService,private router:Router,public dialog: MatDialog) { }

  ngOnInit(): void {
   this.getSujet();
  }

  getSujet()
  {
    this.VS.FindSujet().subscribe(
      sujet=>{
        console.log(sujet);
        this.sujets=sujet;
      })
  }


  onOpenDialog(task: Sujet) {
    console.log(task);

    const dialogRef = this.dialog.open(ShowComponent, {
      data: task,
    });

    dialogRef.afterClosed().subscribe((result) => {
      this.getSujet();
      this.refreshEmitter.emit(true);
    });


  }


  Search()
  {
    if(this.nomSujet == "" )
    {
      this.ngOnInit
    }
    else{
      this.sujets=this.sujets.filter(res=>{
        return res.nomSujet.toLocaleLowerCase()
        .match(this.nomSujet.toLocaleLowerCase());
      })

    }


  }

key : string ='nomSujet'
reverse:boolean=false;
  Sort(key:any)
  {
    this.key= key;
    this.reverse=!this.reverse;

  }


















}
