import { Component, OnDestroy, OnInit } from "@angular/core";
import {
  collection,
  collectionData,
  CollectionReference,
  DocumentData,
  Firestore,
} from "@angular/fire/firestore";
import { Router } from "@angular/router";
import { Observable, Subscription } from "rxjs";
import { Chatroom } from "../chatroom";
import { ChatroomService } from "../chatroom.service";

@Component({
  selector: "app-chatrooms-table",
  templateUrl: "./chatrooms-table.component.html",
  styleUrls: ["./chatrooms-table.component.scss"],
})
export class ChatroomsTableComponent implements OnInit, OnDestroy {
  item$: Observable<any[]>;
  col: CollectionReference<DocumentData>;
  constructor(private service: ChatroomService, private firestore: Firestore,private router:Router) {
    this.col = collection(firestore, "items");
    this.item$ = collectionData(this.col);
  }
  rooms: Chatroom[];
  visible: boolean = false;
  subscription: Subscription;
  subscription2: Subscription;
  subscription3: Subscription;



  ngOnInit(): void {
    this.getAllRooms();
  }

  OnAddRoomClicked():void{
    this.router.navigateByUrl("/chat/add-room");
  }

  toggleVisibility() {
    this.visible = !this.visible;
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }
  getAllRooms() {
    this.subscription = this.service.getAllRooms().subscribe((rooms) => {
      console.log(rooms);
      rooms.forEach((room) => {
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
    this.subscription3 = this.service
      .deleteRoom(room)
      .subscribe((data) => this.getAllRooms());
  }

  handleNavigateToEdit(room:Chatroom):void{
    console.log(room);
    this.router.navigateByUrl("/chat/"+room.id)
  }
  handleNavigateToChatRoomUsersList(room:Chatroom):void{
    console.log("clicked")
    if(room.users){
      if(room.users?.length>0){
        this.router.navigateByUrl(`/chat/${room.id}/user-list`)
      }
    }
}
}
