import { Component, EventEmitter, OnInit, Output } from "@angular/core";
import { MatDialog } from "@angular/material/dialog";
import { ActivatedRoute, Router } from "@angular/router";
import Swal from "sweetalert2";
import { AddUserToRoomComponent } from "../add-user-to-room/add-user-to-room.component";
import { Chatroom } from "../chatroom";
import { ChatroomService } from "../chatroom.service";

@Component({
  selector: "app-room-user-list",
  templateUrl: "./room-user-list.component.html",
  styleUrls: ["./room-user-list.component.scss"],
})
export class RoomUserListComponent implements OnInit {
  constructor(
    private dialog: MatDialog,
    private service: ChatroomService,
    private router: Router,
    private route: ActivatedRoute
  ) {}

  room: Chatroom;
  roomId: number;
  users: any[];
  deletedButtonLoadingState: Map<any, boolean> = new Map();
  bannButtonLoadingState: Map<any, boolean> = new Map();
  unbannButtonLoadingState: Map<any, boolean> = new Map();
  disabled: boolean = false;
  banned: boolean = false;
  bannList: any[];
  /**
   * TODO: to get eacht loading button state we must create a hashMap
   *        and populate each Button with its loading state
   *        PS: we must initialize the state to isLoading:false right after fetching the users
   *
   */

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
    this.service.findRoomById(id).subscribe((room: any) => {
      console.log(room);
      this.room = room;
      this.bannList = room.bannList;
      console.log(this.bannList);
      this.retrieveUsersByChatroom(room);
    });
  }

  retrieveUsersByChatroom(room: Chatroom) {
    this.service.getUsersByRoom(room.id).subscribe((users) => {
      this.users = users;
      this.room.users = users;

      console.log(users);
      this.populateLoadingData();
    });
  }
  OnAddUserPressed(): void {
    //TODO:implement dialog select list
  }

  populateLoadingData(): void {
    this.users.forEach((user) => {
      this.deletedButtonLoadingState.set(user, false);
      this.bannButtonLoadingState.set(user, false);
      this.unbannButtonLoadingState.set(user, false);
    });
  }

  onDeleteUserPressed(user: any) {
    //TODO: implement remove user from chatroom
    //this.isDeletingLoading = true;
    this.deletedButtonLoadingState.set(user, true);
    this.service.bannUserFromRoom(user.id, this.room.id).subscribe({
      next: () => this.retrieveUsersByChatroom(this.room),
      error: (error) => {
        Swal.fire({
          icon: "error",
          title: "failure",
          text: "Something went wrong!",
          footer: '<a href="">try again ?</a>',
        });
        //window.alert(error.message);
        this.deletedButtonLoadingState.set(user, false);
      },
      complete: () => this.deletedButtonLoadingState.set(user, false),
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

  bannUserFromChatRoom(user: any, roomID: number) {
    this.bannButtonLoadingState.set(user, true);
    this.service.bannUserFromChatRoom(user.id, roomID).subscribe({
      next: () => {
        this.retrieveUsersByChatroom(this.room);
        this.disabled = !this.disabled;
        Swal.fire("success", "user banned succesfully", "success");
        setTimeout(() => {
          window.location.reload();
        }, 1000);
      },
      error: (error) => {
        Swal.fire({
          icon: "error",
          title: "failure",
          text: "Something went wrong!",
          footer: '<a href="">try again ?</a>',
        });

        this.bannButtonLoadingState.set(user, false);
      },
      complete: () => this.bannButtonLoadingState.set(user, false),
    });
  }

  checkUserBannedFromRoom(user: any, roomID: number) {
    this.service.checkUserBannedFromRoom(roomID, user.id).subscribe({
      next: (result: any) => {
        console.log(result);
        this.banned = result;
        //Swal.fire("success", "user banned succesfully", "success");
      },
      error: (error) => {
        console.log(error);
        Swal.fire({
          icon: "error",
          title: "failure",
          text: "Something went wrong!",
          footer: '<a href="">try again ?</a>',
        });

        this.bannButtonLoadingState.set(user, false);
      },
    });
  }

  unbannUserFromChatRoom(user: any, roomID: number) {
    this.unbannButtonLoadingState.set(user, true);
    this.service.unbannUserFromChatRoom(user.id, roomID).subscribe({
      next: () => {
        this.retrieveUsersByChatroom(this.room);
        this.disabled = !this.disabled;
        Swal.fire("success", "user unbanned succesfully", "success");
      },
      error: (error) => {
        Swal.fire({
          icon: "error",
          title: "failure",
          text: "Something went wrong!",
          footer: '<a href="">try again ?</a>',
        });

        this.unbannButtonLoadingState.set(user, false);
      },
      complete: () => this.unbannButtonLoadingState.set(user, false),
    });
  }
}
