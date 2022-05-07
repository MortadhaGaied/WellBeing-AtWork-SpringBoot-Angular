import {Component,AfterViewInit,EventEmitter,Output, Input,ViewChild, OnInit} from '@angular/core';
import {Sujet} from "../../Models/Evaluation/Sujet";
import {Router} from "@angular/router";
import {VoteService} from "../Vote.service";
import { MatDialog } from '@angular/material/dialog';
import {ShowComponent} from "../show/show.component";

@Component({
  selector: 'app-sujet-thumbnail',
  templateUrl: './sujet-thumbnail.component.html',
  /*
  template: `



  <!--  <div   class="well hoverwell thumbnail">

        <h2>{{sujet.nomSujet| uppercase }}/<h2>
      <div>Date:{{sujet.dateAjout}}</div>
      <div>Description: {{sujet.description}}</div>
      <a routerLink="/evaluation/detail-vote/{{sujet.id}}" >click here</a>
      <button  (click)="onOpenDialog(sujet)">update</button>

    </div>
    -->



  `,
  */
  styles:[

    `
      .green { color:#003300 !important; }
      .bold { font-weight:bold; }
      .thumbnail { min-height: 210px; padding-left: 10px; background-color:#bbb; margin-bottom:10px; }
      .pad-left { margin-left: 10px; }
      .well div { color: #343a40; }`
  ]
})
export class SujetThumbnailComponent implements OnInit  {















  @Input()
  sujet:Sujet;
  sujetId: number;


  constructor(private router:Router,private VS:VoteService,public dialog: MatDialog) { }
  sujets: Sujet[];

  ngOnInit(): void {
  }











  getSujet()
  {
    this.VS.FindSujet().subscribe(
      sujet=>{
        console.log(sujet);
        this.sujets=sujet;
      })
  }
  @Output() refreshEmitter = new EventEmitter<boolean>();
  onOpenDialog(sujet:Sujet)
  {
    console.log(sujet)
    const dialogRef = this.dialog.open(ShowComponent, {
      data: sujet,
    });

    dialogRef.afterClosed().subscribe((result) => {
      this.getSujet();
      this.refreshEmitter.emit(true);
    });

  }







































}
