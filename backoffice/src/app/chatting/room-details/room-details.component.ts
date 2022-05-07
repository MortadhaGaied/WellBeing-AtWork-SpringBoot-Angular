import { Component, OnInit } from "@angular/core";
import { ActivatedRoute, Params, Router } from "@angular/router";
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
    this.route.params.subscribe({
      next: (params: Params) => (this.roomId = params["id"]),
      error: (error) => window.alert(error.message),
    });

    //retrieve room by id From the server
    this.service.findRoomById(this.roomId).subscribe({
      next: (room: Chatroom) => (this.currentRoom = room),
      error: (error) => (this.isLoading = false),
      complete: () => {
        this.getUsersByChatRoom(this.roomId);
        this.isLoading = false;
        console.log(this.currentRoom);
      },
    });
  }

  getUsersByChatRoom(roomID: number): void {
    this.service.getUsersByRoom(roomID).subscribe({
      next: (users: any[]) => (this.currentRoom.users = users),
      error: (error) => window.alert(error.message),
    });
  }

  checkChatRoomFull(): boolean {
    let result = false;
    console.log(this.currentRoom.users?.length);
    console.log(this.currentRoom.capacity);
    if (this.currentRoom.users?.length === this.currentRoom.capacity) {
      result = true;
    }
    console.log(result);
    return result;
  }

  checkRoomISEmpty() {
    console.log("room empty");
    let result = false;
    if (this.currentRoom.users?.length == 0) result = true;
    return result;
  }
}
