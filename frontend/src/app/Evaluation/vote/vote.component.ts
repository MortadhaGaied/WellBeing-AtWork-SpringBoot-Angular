import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { EvaluationService } from '../evaluation.service';
import { Sujet } from '../Sujet';
import { VoteIdea } from '../VoteIdea';

@Component({
  selector: 'app-vote',
  templateUrl: './vote.component.html',
  styleUrls: ['./vote.component.css']
})
export class VoteComponent implements OnInit {

  sujets: Sujet[]=[];

  vote:VoteIdea[]=[]
  sujetId:number
  idUser:number
  

  constructor(private VS:EvaluationService,private router:Router) { }

  ngOnInit(): void {
    this.VS.FindSujet().subscribe(
      sujet=>{
        console.log(sujet);
        this.sujets=sujet;
      })

  }

  YES(sujetId,idUser,vote)
  {

   this.VS.addYes(sujetId,idUser,vote).subscribe(()=>this.router.navigateByUrl(""));
    console.log(this.vote);
  }





}
