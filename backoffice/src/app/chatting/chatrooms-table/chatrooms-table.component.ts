import { Component, OnDestroy, OnInit } from "@angular/core";
import { Subscription } from "rxjs";
import { Chatroom } from "../chatroom";
import { ChatroomService } from "../chatroom.service";

@Component({
  selector: "app-chatrooms-table",
  templateUrl: "./chatrooms-table.component.html",
  styleUrls: ["./chatrooms-table.component.scss"],
})
export class ChatroomsTableComponent implements OnInit, OnDestroy {
  constructor(private service: ChatroomService) {}
  rooms: Chatroom[];
  subscription: Subscription;

  ngOnInit(): void {
    this.getAllRooms();
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }
  getAllRooms() {
    this.subscription = this.service.getAllRooms().subscribe((rooms) => {
      console.log(rooms);
      rooms.forEach(
        (room) => (room.capacity = Math.floor(Math.random() * 100) + 1 + "%")
      );
      this.rooms = rooms;
    });
  }
}
