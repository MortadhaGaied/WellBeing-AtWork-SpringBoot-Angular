import { Component, NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { AddRoomComponent } from "./add-room/add-room.component";
import { ChatRoomsComponent } from "./chat-rooms/chat-rooms.component";
import { EditComponent } from "./edit/edit.component";
import { RoomUserListComponent } from "./room-user-list/room-user-list.component";

export const routes: Routes = [
  {
    path: "",
    children: [{ path: "chat-rooms", component: ChatRoomsComponent }
    ,{path:"add-room",component:AddRoomComponent},
    {path:":id",component:EditComponent},
  {path:":id/user-list",component:RoomUserListComponent}],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ChattingRoutingModule {}
