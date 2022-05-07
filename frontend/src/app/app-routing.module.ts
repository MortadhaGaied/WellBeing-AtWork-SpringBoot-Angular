import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomePageComponent } from './core/home-page/home-page.component';
import { StreamComponent } from './live-stream/stream/stream.component';
import {SurveyComponent} from "./Evaluation/survey/survey.component";
import {RatingComponent} from "./Evaluation/rating/rating.component";
import { GiftsComponent } from './Evaluation/gifts/gifts.component';
import { VoteComponent } from './Evaluation/vote/vote.component';

const routes: Routes = [
  {path:'Survey',component:SurveyComponent},
  {path:'Gifts',component:GiftsComponent},
  {path:'vote',component:VoteComponent},
  { path: '', component: HomePageComponent },

  {path:'Rating',component:RatingComponent},
  { path: 'live-stream', component: StreamComponent },
  { path: '**', redirectTo: '', pathMatch: 'full' },


];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
