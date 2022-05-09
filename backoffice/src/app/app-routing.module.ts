import { NgModule } from "@angular/core";
import { Routes, RouterModule } from "@angular/router";
import { AddEventComponentComponent } from "./event/add-event-component/add-event-component.component";
import { EventsComponent } from "./event/events/events.component";

import { FullComponent } from "./layouts/full/full.component";

export const Approutes: Routes = [
  {
    path: "",
    component: FullComponent,
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
        path: "event",
        loadChildren: () =>
          import("./event/event.module").then((m) => m.EventModule),
      },
   
    ],
  },
  {
    path: "**",
    redirectTo: "/starter",
  },
];
