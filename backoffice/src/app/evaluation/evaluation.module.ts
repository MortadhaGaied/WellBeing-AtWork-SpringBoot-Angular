import {NgModule} from "@angular/core";
import {CommonModule} from "@angular/common";
import {RouterModule} from "@angular/router";
import {ComponentsModule} from "../component/component.module";
import {EvaluationComponent} from "./evaluation/evaluation.component";
import {routes} from "./evaluation-routing.module";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";

import { QVTComponent } from './qvt/qvt.component';
import { SuccessPageComponent } from './success-page/success-page.component';
import { VoteComponent } from './vote/vote.component';
import { PointsAndGiftsComponent } from './points-and-gifts/points-and-gifts.component';
import { AddSujetComponent } from './add-sujet/add-sujet.component';

import { SujetThumbnailComponent } from './sujet-thumbnail/sujet-thumbnail.component';
import { DeatailVoteComponent } from './deatail-vote/deatail-vote.component';
import {ChartsModule} from "ng2-charts";
import { NavigationComponent } from './navigation/navigation.component';

import {MaterialModule} from "./material/material.module";
import { ShowComponent } from './show/show.component';
import { DialogStatComponent } from './dialog-stat/dialog-stat.component';
import { ShowSurveyComponent } from './show-survey/show-survey.component';
import {NgxPrintModule} from 'ngx-print';
import{Ng2SearchPipeModule} from 'ng2-search-filter';
import{Ng2OrderModule} from 'ng2-order-pipe';
import{NgxPaginationModule} from 'ngx-pagination';

@NgModule({
  declarations: [


    QVTComponent,
    SuccessPageComponent,
    VoteComponent,
    PointsAndGiftsComponent,
    AddSujetComponent,
    SujetThumbnailComponent,
    DeatailVoteComponent,
    NavigationComponent,
    ShowComponent,
    DialogStatComponent,
    ShowSurveyComponent,
    EvaluationComponent,



  ],
  imports: [



    CommonModule,
    RouterModule.forChild(routes),
    ComponentsModule,
    FormsModule,
    ChartsModule,
    MaterialModule,
    ReactiveFormsModule,
    NgxPrintModule,
    Ng2SearchPipeModule,
    Ng2OrderModule,
    NgxPaginationModule









  ]
})
export class EvaluationModule { }
