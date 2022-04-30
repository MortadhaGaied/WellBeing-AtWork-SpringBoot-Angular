import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { CollaborationRoutingModule } from './collaboration-routing.module';
import { CollaborationsComponent } from './collaborations/collaborations.component';
import { AddCollaborationComponent } from './add-collaboration/add-collaboration.component';
import { OfferComponent } from './offer/offer.component';
import { AddOfferComponent } from './add-offer/add-offer.component';


@NgModule({
  declarations: [
    CollaborationsComponent,
    AddCollaborationComponent,
    OfferComponent,
    AddOfferComponent
  ],
  imports: [
    CommonModule,
    CollaborationRoutingModule
  ]
})
export class CollaborationModule { }
