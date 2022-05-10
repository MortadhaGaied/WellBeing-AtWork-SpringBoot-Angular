import { BrowserModule } from "@angular/platform-browser";
import { BrowserAnimationsModule } from "@angular/platform-browser/animations";
import {
  CommonModule,
  LocationStrategy,
  PathLocationStrategy,
} from "@angular/common";
import { NgModule } from "@angular/core";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { HttpClientModule } from "@angular/common/http";
import { RouterModule } from "@angular/router";

import { NgbModule } from "@ng-bootstrap/ng-bootstrap";

import { FullComponent } from "./layouts/full/full.component";

import { NavigationComponent } from "./shared/header/navigation.component";
import { SidebarComponent } from "./shared/sidebar/sidebar.component";

import { Approutes } from "./app-routing.module";
import { AppComponent } from "./app.component";
import { SpinnerComponent } from "./shared/spinner.component";

import {
  PerfectScrollbarModule,
  PERFECT_SCROLLBAR_CONFIG,
} from "ngx-perfect-scrollbar";
import { PerfectScrollbarConfigInterface } from "ngx-perfect-scrollbar";
import { authInterceptorProviders } from "./services/auth-services/auth.interceptor";

// import {MatSelectModule} from '@angular/material';
// import { MatFormFieldModule } from '@angular/material';
// import { MatFormFieldModule } from "@angular/material/form-field";
import { NgxPaginationModule } from "ngx-pagination";
import { provideFirebaseApp } from "@angular/fire/app";
import { provideFirestore } from "@angular/fire/firestore";
import { environment } from "../environments/environment.prod";
import { initializeApp } from "firebase/app";
import { getFirestore } from "firebase/firestore";

const DEFAULT_PERFECT_SCROLLBAR_CONFIG: PerfectScrollbarConfigInterface = {
  suppressScrollX: true,
  wheelSpeed: 1,
  wheelPropagation: true,
  minScrollbarLength: 20,
};

@NgModule({
  declarations: [
    AppComponent,
    SpinnerComponent,
    FullComponent,
    NavigationComponent,
    SidebarComponent,
  ],
  imports: [
    CommonModule,
    BrowserModule,
    BrowserAnimationsModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    NgbModule,
    BrowserModule,
    NgxPaginationModule,

    RouterModule.forRoot(Approutes, {
      useHash: false,
      relativeLinkResolution: "legacy",
    }),
    PerfectScrollbarModule,
    provideFirebaseApp(() => initializeApp(environment.firebaseConfig)),
    provideFirestore(() => getFirestore()),
  ],
  providers: [
    {
      provide: LocationStrategy,
      useClass: PathLocationStrategy,
    },
    {
      provide: PERFECT_SCROLLBAR_CONFIG,
      useValue: DEFAULT_PERFECT_SCROLLBAR_CONFIG,
    },

    authInterceptorProviders,
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
