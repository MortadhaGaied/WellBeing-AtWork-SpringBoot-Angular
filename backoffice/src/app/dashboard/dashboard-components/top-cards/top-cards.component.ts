import { Component, Input, OnInit } from "@angular/core";
import { topcard, topcards } from "./top-cards-data";

@Component({
  selector: "app-top-cards",
  templateUrl: "./top-cards.component.html",
})
export class TopCardsComponent implements OnInit {
  @Input() topcards: topcard[];

  constructor() {}

  ngOnInit(): void {}
}
