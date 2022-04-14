import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { EventRoutingModule,routes } from './event-routing.module';
import { EventsComponent } from './events/events.component';
import { RouterModule } from '@angular/router';
import { ComponentsModule } from 'app/component/component.module';


@NgModule({
  declarations: [
    EventsComponent
  ],
  imports: [
    CommonModule,
    RouterModule.forChild(routes),ComponentsModule
  ]
})
export class EventModule { }
