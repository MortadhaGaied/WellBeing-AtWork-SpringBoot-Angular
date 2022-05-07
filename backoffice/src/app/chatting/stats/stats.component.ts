import { HttpClient } from "@angular/common/http";
import { Component, OnInit } from "@angular/core";
import { ChartDataSets, ChartOptions, ChartType } from "chart.js";
import { Label } from "ng2-charts";
import { ChatroomService } from "../chatroom.service";

@Component({
  selector: "app-stats",
  templateUrl: "./stats.component.html",
  styleUrls: ["./stats.component.scss"],
})
export class StatsComponent implements OnInit {
  constructor(private service: ChatroomService) {
    //this.service.get
  }

  ngOnInit(): void {
    //this.
  }

  barChartOptions: ChartOptions = {
    responsive: true,
  };
  barChartLabels: Label[] = ["Amine", "AuckFmine", "aze", "azeazeaze"];
  barChartType: ChartType = "bar";
  barChartLegend = true;
  barChartPlugins: any[] = [];
  barChartData: ChartDataSets[] = [
    { data: [10, 12, 0, 0], label: "Messages Per User" },
  ];
}
