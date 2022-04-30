import { Component, OnInit } from "@angular/core";
import { ActivatedRoute, Router } from "@angular/router";
import { Chatroom } from "../chatroom";
import { ChatroomService } from "../chatroom.service";

@Component({
  selector: "app-room-details",
  templateUrl: "./room-details.component.html",
  styleUrls: ["./room-details.component.scss"],
})
export class RoomDetailsComponent implements OnInit {
  constructor(
    private service: ChatroomService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  roomId: number;
  currentRoom: Chatroom;
  isLoading: boolean;

  ngOnInit(): void {
    this.getRoomById();
  }

  getRoomById(): void {
    this.isLoading = true;
    //retrieve roomId from route
    this.route.params.subscribe((params) => (this.roomId = params["id"]));

    //retrieve room by id From the server
    this.service.findRoomById(this.roomId).subscribe(
      (room) => (this.currentRoom = room),
      () => (this.isLoading = false),
      () => (this.isLoading = false)
    );
  }
}
