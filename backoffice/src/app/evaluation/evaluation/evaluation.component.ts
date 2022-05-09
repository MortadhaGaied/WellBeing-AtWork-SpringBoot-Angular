import { Component, OnInit } from '@angular/core';
import {Survey} from "../../Models/Evaluation/Survey";
import {QVTEvaluationService} from "../qvtevaluation.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-evaluation',
  templateUrl: './evaluation.component.html',
  styleUrls: ['./evaluation.component.scss']
})
export class EvaluationComponent implements OnInit {

  survey: Survey[];

  constructor(private router:Router,private QVtEvaluation:QVTEvaluationService) { }

  ngOnInit(): void {
  /*  this.QVtEvaluation.SendSurvey().subscribe(
      surveys=>{
        console.log(surveys);
        this.survey=surveys;
      })

   */
  }

  Go()
  {
    this.router.navigate(['/evaluation/QVT'])
  }

  GoVote()
  {
    this.router.navigate(['/evaluation/Vote'])
  }



  GoGifts()
  {
    this.router.navigate(['/evaluation/UsersGifts'])
  }


}
