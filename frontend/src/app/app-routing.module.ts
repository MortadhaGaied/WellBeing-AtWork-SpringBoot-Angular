import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomePageComponent } from './core/home-page/home-page.component';
import { StreamComponent } from './live-stream/stream/stream.component';
import {EventListComponent} from './event/event-list/event-list.component'
import { EventDetailComponent } from './event/event-detail/event-detail.component';
import { GameComponent } from './event/game/game.component';
import { WeatherComponent } from './event/weather/weather.component';
import { CalendarComponent } from './event/calendar/calendar.component';
import { FeedbackComponent } from './event/feedback/feedback.component';
const routes: Routes = [
  { path: '', component: HomePageComponent },
  { path: 'live-stream', component: StreamComponent },
  {path: 'event-list',component:EventListComponent},
  {path: 'event-detail/:id',component:EventDetailComponent},
  {path: 'game',component:GameComponent},
  {path: 'weather/:id',component:WeatherComponent},
  {path: 'calendar',component:CalendarComponent},
  {path: 'feedback',component:FeedbackComponent},



  { path: '**', redirectTo: '', pathMatch: 'full' },
 
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
