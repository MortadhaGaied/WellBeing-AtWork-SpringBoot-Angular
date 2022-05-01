import { AddCollaborationComponent } from './add-collaboration/add-collaboration.component';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CollaborationsComponent } from './collaborations/collaborations.component';

const routes: Routes = [
  {
    path: "",
    children: [{ path: "collaborations", component: CollaborationsComponent },{ path: "Addcollaborations", component: AddCollaborationComponent }],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CollaborationRoutingModule { }
