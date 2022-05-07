import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { UserIndexComponent } from "./user-index/user-index.component";

const routes: Routes = [
  {
    path: "",
    component: UserIndexComponent,
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class UserRoutingModule {}
