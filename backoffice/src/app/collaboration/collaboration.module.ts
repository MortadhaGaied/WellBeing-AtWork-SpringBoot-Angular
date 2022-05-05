import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NgxMapboxGLModule } from 'ngx-mapbox-gl';
import { CollaborationRoutingModule } from './collaboration-routing.module';
import { CollaborationsComponent } from './collaborations/collaborations.component';
import { AddCollaborationComponent } from './add-collaboration/add-collaboration.component';
import { OfferComponent } from './offer/offer.component';
import { AddOfferComponent } from './add-offer/add-offer.component';
import { FormsModule } from '@angular/forms';
import { MapComponent } from './map/map.component';
import { environment } from '../../environments/environment';
import { UpdateCollaborationComponent } from './update-collaboration/update-collaboration.component';
import { UpdateOfferComponent } from './update-offer/update-offer.component';
import { NgxPaginationModule } from 'ngx-pagination';
import { ShowCollaborationComponent } from './show-collaboration/show-collaboration.component';
import { ShowOffersComponent } from './show-offers/show-offers.component';


@NgModule({
  declarations: [
    CollaborationsComponent,
    AddCollaborationComponent,
    OfferComponent,
    AddOfferComponent,
    MapComponent,
    UpdateOfferComponent,
    UpdateCollaborationComponent,
    ShowCollaborationComponent,
    ShowOffersComponent,
  ],
  imports: [
    CommonModule,
    CollaborationRoutingModule,
    NgxPaginationModule,
    FormsModule,
    NgxMapboxGLModule.withConfig({
      accessToken: environment.mapbox.accessToken,
    }),
  ]
})
export class CollaborationModule { }
