import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { EventRoutingModule,routes } from './event-routing.module';
import { EventsComponent } from './events/events.component';
import { RouterModule } from '@angular/router';
import { AddEventComponentComponent } from './add-event-component/add-event-component.component';
import { FormsModule } from '@angular/forms';


@NgModule({
  declarations: [
    EventsComponent,
    AddEventComponentComponent
  ],
  imports: [
    CommonModule,
    RouterModule.forChild(routes),
    FormsModule
  ]
})
export class EventModule { }
