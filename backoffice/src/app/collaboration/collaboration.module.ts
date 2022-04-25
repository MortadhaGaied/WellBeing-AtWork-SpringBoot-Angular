import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { CollaborationRoutingModule } from './collaboration-routing.module';
import { CollaborationsComponent } from './collaborations/collaborations.component';


@NgModule({
  declarations: [
    CollaborationsComponent
  ],
  imports: [
    CommonModule,
    CollaborationRoutingModule
  ]
})
export class CollaborationModule { }
