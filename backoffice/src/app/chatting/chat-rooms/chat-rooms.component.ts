import { Component, OnInit } from "@angular/core";
import { topcard } from "../../dashboard/dashboard-components/top-cards/top-cards-data";

@Component({
  selector: "app-chat-rooms",
  templateUrl: "./chat-rooms.component.html",
  styleUrls: ["./chat-rooms.component.scss"],
})
export class ChatRoomsComponent implements OnInit {
  constructor() {}
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
      title: Math.floor(Math.random() * 100) + 1 + "",
      subtitle: "OverAll Capacity",
    },
  ];
  ngOnInit(): void {}
}
