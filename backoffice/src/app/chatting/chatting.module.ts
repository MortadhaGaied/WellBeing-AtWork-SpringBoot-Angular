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
import { AddRoomComponent } from "./add-room/add-room.component";
import { EditComponent } from "./edit/edit.component";
import { RoomUserListComponent } from "./room-user-list/room-user-list.component";
import { RoomDetailsComponent } from "./room-details/room-details.component";
import { SearchFilterPipe } from "./pipes/search-filter.pipe";
import { Ng2OrderModule } from "ng2-order-pipe";
import {
  NgxPaginationModule,
  PaginationControlsComponent,
} from "ngx-pagination";
import { MatDialogModule } from "@angular/material/dialog";
import { AddUserToRoomComponent } from "./add-user-to-room/add-user-to-room.component";
import { UserSearchFilterPipe } from "./pipes/user-search-filter.pipe";
import { SweetAlert2Module } from "@sweetalert2/ngx-sweetalert2";
import { ChartsModule } from "ng2-charts";
import { StatsComponent } from './stats/stats.component';
@NgModule({
  declarations: [
    ChatRoomsComponent,
    ChatroomsTableComponent,
    AddRoomComponent,
    EditComponent,
    RoomUserListComponent,
    RoomDetailsComponent,
    SearchFilterPipe,
    AddUserToRoomComponent,
    UserSearchFilterPipe,
    StatsComponent,
  ],
  imports: [
    FormsModule,
    MatSlideToggleModule,
    MatSliderModule,
    CommonModule,
    RouterModule.forChild(routes),
    ComponentsModule,
    DashboardModule,
    Ng2OrderModule,
    NgxPaginationModule,
    DashboardModule,
    MatDialogModule,
    SweetAlert2Module,
    ChartsModule,
  ],
  entryComponents: [AddRoomComponent, AddUserToRoomComponent],
})
export class ChattingModule {}
