import { Component, OnInit } from "@angular/core";
import {
  collection,
  collectionData,
  CollectionReference,
  DocumentData,
  Firestore,
} from "@angular/fire/firestore";
import { Observable } from "rxjs";
import { topcard } from "../../dashboard/dashboard-components/top-cards/top-cards-data";
import { ChatroomService } from "../chatroom.service";

@Component({
  selector: "app-chat-rooms",
  templateUrl: "./chat-rooms.component.html",
  styleUrls: ["./chat-rooms.component.scss"],
})
export class ChatRoomsComponent implements OnInit {
  item$: Observable<any[]>;
  col: CollectionReference<DocumentData>;
  col2: CollectionReference<DocumentData>;
  totalMessagesCount: number = 0;
  totalRoomsCount: number = 0;
  data: any = [{ auckfmine: 0 }];
  constructor(private service: ChatroomService, private firestore: Firestore) {
    //this.col = collection(firestore, "items");
    this.col2 = collection(firestore, "top-chatters");
    this.item$ = collectionData(this.col2);
  }

  ngOnInit(): void {
    this.item$.subscribe((data) => {
      this.topcards[2].title = "id : " + data[0].auckfmine;
    });
    this.getMessagesCount();

    this.getRoomCount();
    console.log("rooms count : " + this.totalRoomsCount);
  }

  getMessagesCount(): void {
    this.service.getAllMessages().subscribe((num) => {
      console.log(num);
      this.topcards[0].title = num + "";
      this.totalMessagesCount = num;
    });
  }

  getRoomCount(): void {
    this.service.getAllRooms().subscribe((rooms) => {
      console.log(rooms.length);
      this.topcards[1].title = rooms.length + "";
      this.totalRoomsCount = rooms.length;
    });
  }
  topcards: topcard[] = [
    {
      bgcolor: "success",
      icon: "bi bi-chat",
      title: this.totalMessagesCount + "",
      subtitle: "Total Messages",
    },
    {
      bgcolor: "danger",
      icon: "bi bi-chat-text",
      title: this.totalRoomsCount + "",
      subtitle: "Total Rooms",
    },
    {
      bgcolor: "warning",
      icon: "bi bi-graph-up-arrow",
      title: this.data[0].toString().split("{") + "",
      subtitle: "top chatter",
    },
  ];
}
