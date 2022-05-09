import { NgModule } from "@angular/core";
import { Routes, RouterModule } from "@angular/router";
import { NewCollabComponent } from "./collaboration/new-collab/new-collab.component";
import { ShowCollabsComponent } from "./collaboration/show-collabs/show-collabs.component";
import { AuthenticationGuard } from "./guards/authentication.guard";

import { FullComponent } from "./layouts/full/full.component";

export const Approutes: Routes = [
  {
    path: "",
    component: FullComponent,
    canActivate: [AuthenticationGuard],
    children: [
      { path: "", redirectTo: "/dashboard", pathMatch: "full" },
      {
        path: "dashboard",
        loadChildren: () =>
          import("./dashboard/dashboard.module").then((m) => m.DashboardModule),
      },
      {
        path: "about",
        loadChildren: () =>
          import("./about/about.module").then((m) => m.AboutModule),
      },
      {
        path: "component",
        loadChildren: () =>
          import("./component/component.module").then(
            (m) => m.ComponentsModule
          ),
      },
      {
        path: "chat",
        loadChildren: () =>
          import("./chatting/chatting.module").then((m) => m.ChattingModule),
      },
      {
        path: "forum",
        loadChildren: () =>
          import("./forum/forum.module").then((m) => m.ForumModule),
      },
      {
        path: "collaboration",
        loadChildren: () =>
          import("./collaboration/collaboration.module").then(
            (m) => m.CollaborationModule
          ),
      },
    ],
  },
  {
    path: "auth",
    loadChildren: () => import("./auth/auth.module").then((m) => m.AuthModule),
  },
  {
    path: "**",
    redirectTo: "/starter",
  },
];
