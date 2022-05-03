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


@NgModule({
  declarations: [
    CollaborationsComponent,
    AddCollaborationComponent,
    OfferComponent,
    AddOfferComponent,
    MapComponent,
  ],
  imports: [
    CommonModule,
    CollaborationRoutingModule,
    FormsModule,
    NgxMapboxGLModule.withConfig({
      accessToken: environment.mapbox.accessToken,
    }),
  ]
})
export class CollaborationModule { }
