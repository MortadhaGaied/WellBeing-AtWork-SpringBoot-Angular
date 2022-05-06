import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AddEventComponentComponent } from './add-event-component/add-event-component.component';
import { CalendarComponent } from './calendar/calendar.component';
import { EventsComponent } from './events/events.component';
import { ChartComponent } from './chart/chart.component';

export const routes: Routes = [
  {
    path:"", 
    children:[
      {path:"events",component:EventsComponent},
      {path:"addEvent", component: AddEventComponentComponent},
      {path:"calendar", component:CalendarComponent },
      {path:"chart", component:ChartComponent },


    ],
  },

  
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class EventRoutingModule { 

}

