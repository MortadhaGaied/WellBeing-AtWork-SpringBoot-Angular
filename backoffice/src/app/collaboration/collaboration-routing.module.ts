import { Component, NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { ShowCollabsComponent } from "./show-collabs/show-collabs.component";

const routes: Routes = [
  {
    path: "",
    children: [
      {
        path: "/all",
        component: ShowCollabsComponent
      }
    ],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class CollaborationRoutingModule { }
