import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";

import { UserRoutingModule } from "./user-routing.module";
import { UserIndexComponent } from "./user-index/user-index.component";
import { NgxMapboxGLModule } from "ngx-mapbox-gl";
import { environment } from "../../environments/environment";

@NgModule({
  declarations: [UserIndexComponent],
  imports: [
    CommonModule,
    UserRoutingModule,
    NgxMapboxGLModule.withConfig({
      accessToken: environment.mapbox.accessToken,
    }),
  ],
})
export class UserModule {}
