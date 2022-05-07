import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {RouterModule, Routes} from "@angular/router";
import {EvaluationComponent} from "./evaluation/evaluation.component";

import {QVTComponent} from "./qvt/qvt.component";
import {VoteComponent} from "./vote/vote.component";
import {PointsAndGiftsComponent} from "./points-and-gifts/points-and-gifts.component";
import {DeatailVoteComponent} from "./deatail-vote/deatail-vote.component";
import {AddSujetComponent} from "./add-sujet/add-sujet.component";
import { ShowSurveyComponent } from './show-survey/show-survey.component';
import { ShowComponent } from './show/show.component';



export const routes: Routes = [
  {
    path: "",
    children: [{ path: "evaluation", component: EvaluationComponent },
      {path:'QVT',component: QVTComponent},
      {path:'Vote',component: VoteComponent},
      {path:'UsersGifts',component: PointsAndGiftsComponent},
      {path:'addSujet',component:AddSujetComponent},
      {path:'detail-vote/:sujetId',component: DeatailVoteComponent},
      {path:'showSurvey',component:ShowSurveyComponent},
      {path:'Update',component:ShowComponent}












    ],
  },


];


@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})

export class EvaluationRoutingModule { }
