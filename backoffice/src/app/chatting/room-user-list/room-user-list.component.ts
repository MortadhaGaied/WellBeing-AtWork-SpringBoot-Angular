import { Component, OnInit } from "@angular/core";
import { MatDialog } from "@angular/material/dialog";
import { ActivatedRoute, Router } from "@angular/router";
import { AddUserToRoomComponent } from "../add-user-to-room/add-user-to-room.component";
import { Chatroom } from "../chatroom";
import { ChatroomService } from "../chatroom.service";

@Component({
  selector: "app-room-user-list",
  templateUrl: "./room-user-list.component.html",
  styleUrls: ["./room-user-list.component.scss"],
})
export class RoomUserListComponent implements OnInit {
  room: Chatroom;
  constructor(
    private dialog: MatDialog,
    private service: ChatroomService,
    private router: Router,
    private route: ActivatedRoute
  ) {}
  roomId: number;
  users: any[];
  isDeletingLoading: boolean = false;

  ngOnInit(): void {
    this.retrieveRoomIdFromPath();
    this.retrieveRoomById(this.roomId);
  }

  retrieveRoomIdFromPath(): void {
    this.route.params.subscribe((params) => {
      this.roomId = params["id"];
      console.log(this.roomId);
    });
  }

  retrieveRoomById(id: number): void {
    this.service.findRoomById(id).subscribe((room) => {
      console.log(room);
      this.room = room;
      this.retrieveUsersByChatroom(room);
    });
  }

  retrieveUsersByChatroom(room: Chatroom) {
    this.service.getUsersByRoom(room.id).subscribe((users) => {
      this.users = users;
      console.log(users);
    });
  }
  OnAddUserPressed(): void {
    //TODO:implement dialog select list
  }

  onDeleteUserPressed(user: any) {
    //TODO: implement remove user from chatroom
    this.isDeletingLoading = true;
    this.service.bannUserFromRoom(user.id, this.room.id).subscribe({
      next: () => this.retrieveUsersByChatroom(this.room),
      error: (error) => {
        window.alert(error.message);
        this.isDeletingLoading = false;
      },
      complete: () => (this.isDeletingLoading = false),
    });
  }

  onAddUserToRoomClicked() {
    //TODO:implement
    this.dialog.open(AddUserToRoomComponent, {
      height: "400px",
      width: "600px",
      data: this.room,
    });
    console.log(this.room);
  }
}
