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
  constructor(private service: ChatroomService, private firestore: Firestore) {
    //this.col = collection(firestore, "items");
    this.col2 = collection(firestore, "top-chatters");
    this.item$ = collectionData(this.col2);
  }
  topcards: topcard[] = [
    {
      bgcolor: "success",
      icon: "bi bi-chat",
      title: Math.floor(Math.random() * 100) + 1 + "",
      subtitle: "Total Messages",
    },
    {
      bgcolor: "danger",
      icon: "bi bi-chat-text",
      title: "2",
      subtitle: "Total Rooms",
    },
    {
      bgcolor: "warning",
      icon: "bi bi-graph-up-arrow",
      title: "", //this.item$.subscribe() + "",
      subtitle: "top chatter",
    },
  ];
  ngOnInit(): void {}
}
