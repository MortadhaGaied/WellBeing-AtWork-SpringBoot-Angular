import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";

import { ChattingRoutingModule, routes } from "./chatting-routing.module";
import { ChatRoomsComponent } from "./chat-rooms/chat-rooms.component";
import { RouterModule } from "@angular/router";
import { TableComponent } from "../component/table/table.component";
import { ComponentsModule } from "../component/component.module";
import { ChatroomsTableComponent } from "./chatrooms-table/chatrooms-table.component";
import { DashboardModule } from "../dashboard/dashboard.module";
import { MatSliderModule } from "@angular/material/slider";
import { MatSlideToggleModule } from "@angular/material/slide-toggle";
import { FormsModule } from "@angular/forms";
@NgModule({
  declarations: [ChatRoomsComponent, ChatroomsTableComponent],
  imports: [
    FormsModule,
    MatSlideToggleModule,
    MatSliderModule,
    CommonModule,
    RouterModule.forChild(routes),
    ComponentsModule,
    DashboardModule,
  ],
})
export class ChattingModule {}
