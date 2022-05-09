import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { EventRoutingModule,routes } from './event-routing.module';
import { EventsComponent } from './events/events.component';
import { RouterModule } from '@angular/router';
import { AddEventComponentComponent } from './add-event-component/add-event-component.component';
import { FormsModule ,ReactiveFormsModule, } from '@angular/forms';
import {MatChipsModule} from '@angular/material/chips';
import {MatIconModule} from '@angular/material/icon';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatDialogModule} from '@angular/material/dialog';

import { HttpClientModule , HttpClientJsonpModule} from '@angular/common/http';
import { MbscModule } from '@mobiscroll/angular';
import { ChartsModule } from 'ng2-charts';
import { FullCalendarModule } from '@fullcalendar/angular';
import { UpdateEventComponent } from './update-event/update-event.component';
import { ChartComponent } from './chart/chart.component'; 



@NgModule({
  declarations: [
    EventsComponent,
    AddEventComponentComponent,
    UpdateEventComponent,
    ChartComponent,
    

  ],
  imports: [
    CommonModule,
    RouterModule.forChild(routes),
    FormsModule,
    MatChipsModule,
    MatIconModule,
    MatFormFieldModule,
    MatDialogModule,
    FullCalendarModule,
    HttpClientModule  ,
    MbscModule,
    ReactiveFormsModule,
    HttpClientJsonpModule,
    ChartsModule


  ]
})
export class EventModule { }
