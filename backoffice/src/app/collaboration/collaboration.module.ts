import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ShowCollabsComponent } from './show-collabs/show-collabs.component';
import { NewCollabComponent } from './new-collab/new-collab.component';
import { RouterModule, Routes } from '@angular/router';
import { CollaborationRoutingModule } from './collaboration-routing.module';


@NgModule({
  declarations: [
    NewCollabComponent,
    ShowCollabsComponent
  ],
  imports: [
    CommonModule,
    CollaborationRoutingModule
  ],
})
export class CollaborationModule { }
