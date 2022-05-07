import { Component,Inject, OnInit } from '@angular/core';
import {VoteService} from "../Vote.service";
import {Sujet} from "../../Models/Evaluation/Sujet";
import {ActivatedRoute, Router} from "@angular/router";

import { ChartOptions, ChartType } from 'chart.js';
import { Color, Label, SingleDataSet } from 'ng2-charts';
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from "@angular/material/dialog";
import {HttpClient} from "@angular/common/http";
import {FormBuilder} from "@angular/forms";


@Component({
  selector: 'app-deatail-vote',
  templateUrl: './deatail-vote.component.html',
  styleUrls: ['./deatail-vote.component.scss']
})
export class DeatailVoteComponent implements OnInit {





    sujets:Array<Sujet> ;

    name: any;

   sujetId: number;
   value:any;

  constructor( private VS:VoteService,private router:Router,private http:HttpClient,
              private route: ActivatedRoute
              ) {}



  public doughnutChartOptions: ChartOptions = {
    responsive: true,
    legend: {
      display: false,
    },
    cutoutPercentage: 80,
  };
  public doughnutChartLabels: Label[] = [];
  public doughnutChartData: SingleDataSet = [];
  public doughnutChartType: ChartType = 'doughnut';
  public doughnutChartColor: Color[] = [
    { backgroundColor: ['#f68059', '#ffbf3a'] },
  ];


  public typeData: Array<Sujet> = [];



  ngOnInit(): void {
  this.sujetId = this.route.snapshot.params['sujetId'];
    this.VS.findNomdesUsersVoter(this.sujetId).subscribe(data=>{
      this.sujets=data;
      this.name=this.sujets;

    });
 this.countNByesandNo(this.sujetId);
  }


countNByesandNo(sujetId:number)
{
  this.doughnutChartData = [];
  this.doughnutChartLabels = [];
  this.VS.countYesandNo(this.sujetId).subscribe(
    (d) => {

      this.typeData = d;
     console.log(this.typeData)

      d.forEach((type: Sujet) => {
        this.doughnutChartData.push(type.nbNo,type.nbYes);
        this.doughnutChartLabels.push("No","Yes");

        console.log(this.doughnutChartData)
      });

    },
    (error) => {
      console.error(error);
    }
  );

}




  refreshEmitter() {
    this.countNByesandNo(this.sujetId);
  }














}
