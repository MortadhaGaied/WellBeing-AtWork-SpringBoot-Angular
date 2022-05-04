import { UpdateOfferComponent } from './offer/update-offer/update-offer.component';
import { OfferComponent } from './offer/offer.component';
import { AddOfferComponent } from './add-offer/add-offer.component';
import { AddCollaborationComponent } from './add-collaboration/add-collaboration.component';
import { NgModule } from '@angular/core';

import { CollaborationsComponent } from './collaborations/collaborations.component';
import { RouterModule, Routes } from '@angular/router';

const routes: Routes = [
  {
    path: "",
    children: [{ path: "collaborations", component: CollaborationsComponent },{ path: "Addcollaborations", component: AddCollaborationComponent },
    { path: "Offers", component: OfferComponent },{ path: "Addoffers", component: AddOfferComponent },{ path: "offers", component: OfferComponent },
    { path: "Update-offer", component: UpdateOfferComponent }],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CollaborationRoutingModule { }
