import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { EventRoutingModule,routes } from './event-routing.module';
import { EventsComponent } from './events/events.component';
import { RouterModule } from '@angular/router';
import { AddEventComponentComponent } from './add-event-component/add-event-component.component';
import { FormsModule } from '@angular/forms';
import {MatChipsModule} from '@angular/material/chips';
import {MatIconModule} from '@angular/material/icon';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatDialogModule} from '@angular/material/dialog';
@NgModule({
  declarations: [
    EventsComponent,
    AddEventComponentComponent

  ],
  imports: [
    CommonModule,
    RouterModule.forChild(routes),
    FormsModule,
    MatChipsModule,
    MatIconModule,
    MatFormFieldModule,
    MatDialogModule

  ]
})
export class EventModule { }
