import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { CollaborationRoutingModule } from './collaboration-routing.module';
import { CollaborationsComponent } from './collaborations/collaborations.component';
import { AddCollaborationComponent } from './add-collaboration/add-collaboration.component';


@NgModule({
  declarations: [
    CollaborationsComponent,
    AddCollaborationComponent
  ],
  imports: [
    CommonModule,
    CollaborationRoutingModule
  ]
})
export class CollaborationModule { }
