import { Component, OnInit } from '@angular/core';
import { QVTEvaluationService } from '../qvtevaluation.service';
import {Survey} from "../../Models/Evaluation/Survey";

@Component({
  selector: 'app-show-survey',
  templateUrl: './show-survey.component.html',
  styleUrls: ['./show-survey.component.scss']
})
export class ShowSurveyComponent implements OnInit {

  constructor(private QVtEvaluation:QVTEvaluationService) { }


  survey: Survey[];

  ngOnInit(): void {

    this.QVtEvaluation.SendSurvey().subscribe(
      surveys=>{
        console.log(surveys);
        this.survey=surveys;
      })

  }






}
