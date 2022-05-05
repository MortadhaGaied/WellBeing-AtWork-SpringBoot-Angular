import { Component, Inject, OnDestroy, OnInit } from "@angular/core";
import {
  collection,
  collectionData,
  CollectionReference,
  DocumentData,
  Firestore,
} from "@angular/fire/firestore";
import { Router } from "@angular/router";
import { from, Observable, Subscription } from "rxjs";
import { Chatroom } from "../chatroom";
import { ChatroomService } from "../chatroom.service";
import {
  MatDialog,
  MatDialogConfig,
  MAT_DIALOG_DATA,
} from "@angular/material/dialog";
import { AddRoomComponent } from "../add-room/add-room.component";
import { EditComponent } from "../edit/edit.component";

@Component({
  selector: "app-chatrooms-table",
  templateUrl: "./chatrooms-table.component.html",
  styleUrls: ["./chatrooms-table.component.scss"],
})
export class ChatroomsTableComponent implements OnInit, OnDestroy {
  item$: Observable<any[]>;
  col: CollectionReference<DocumentData>;
  constructor(
    private service: ChatroomService,
    private firestore: Firestore,
    private router: Router,
    private dialog: MatDialog
  ) {
    this.col = collection(firestore, "items");
    this.item$ = collectionData(this.col);
  }
  rooms: Chatroom[] = [];
  visible: boolean = false;
  subscription: Subscription;
  searchValue: string = "";
  key: String = "id";
  reversed: boolean = false;
  currentPage: number = 1;
  itemsPerPage: number = 3;
  fields = ["", "id", "roomName", "visible", "status", "cap"];

  ngOnInit(): void {
    this.getAllRooms();
  }
  sort(key: string) {
    this.key = key;
    this.reversed = !this.reversed;
  }

  handleSelectFilter(event: any) {
    this.sort(event.target.value);
  }
  OnAddRoomClicked(): void {
    this.dialog.open(AddRoomComponent);
    //this.router.navigateByUrl("/chat/add-room");
  }

  toggleVisibility() {
    this.visible = !this.visible;
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }
  getAllRooms() {
    //retrieve all rooms
    this.subscription = this.service.getAllRooms().subscribe((rooms) => {
      console.log(rooms);
      rooms.forEach((room) => {
        //for each room we retreive the list of its users
        this.service.getUsersByRoom(room.id).subscribe((users) => {
          room.users = users;
          room.cap =
            Math.round((room.users?.length / room.capacity) * 100) + "%";
        });
      });
      this.rooms = rooms;
    });
  }
  updateRoom(room: Chatroom) {
    this.service.updateRoom(room).subscribe((data) => this.getAllRooms());
  }
  deleteRoom(room: Chatroom) {
    this.service.deleteRoom(room).subscribe((data) => this.getAllRooms());
  }

  handleNavigateToEdit(room: Chatroom): void {
    console.log(room);
    this.dialog.open(EditComponent, { data: room });
  }
  handleNavigateToChatRoomUsersList(room: Chatroom): void {
    //console.log("clicked");
    if (room.users) {
      if (room.users?.length > 0) {
        this.router
          .navigateByUrl(`/chat/${room.id}/user-list`)
          .then(() => window.location.reload());
      }
    }
  }
  handleNavigateToDetails(room: Chatroom) {
    this.router.navigateByUrl(`/chat/${room.id}/details`);
  }
}
