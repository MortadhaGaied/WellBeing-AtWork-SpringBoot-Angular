import { Component, OnInit } from '@angular/core';
import { ChartDataSets, ChartOptions } from 'chart.js';
import { Color, Label } from 'ng2-charts';
import { EventService } from '../event.service';
import { Event } from '../../Models/Forum/Event/Event';
@Component({
  selector: 'app-chart',
  templateUrl: './chart.component.html',
  styleUrls: ['./chart.component.scss']
})
export class ChartComponent implements OnInit {
  constructor(private _service:EventService ) { }
  
  listEvent : Event[];
  updateId : number;
  lineChartData: ChartDataSets[]=[];
  lineChartLabels: Label[] =[];
  lineChartColors: Color[] =[];
  lineChartPlugins = [];
  lineChartLegend:boolean=false;
  lineChartType:string='';
  lineChartOptions:any;
  rev:number[]=[];
  eventNames:string[]=[];
  ngOnInit(): void {

    this._service.getAllEvent().subscribe(res=>{
      console.log(res);
      this.listEvent=res;
      res.forEach(e=>
        {
          this._service.getEventRevenu(e.idEvent).subscribe(
            revenue=>{
              console.log(revenue);
              this.rev.push(revenue);
            });
          this.eventNames.push(e.eventName);

        });
      this.lineChartData = [
        { 
          data: this.rev, label: 'Revenue' 
        },
      ];
     
      this.lineChartLabels = this.eventNames;
     
      this.lineChartOptions = {
        responsive: true,
      };
     
      this.lineChartColors= [
        {
          borderColor: 'black',
          backgroundColor: 'rgba(255,255,0,0.28)',
        },
      ];
     
      this.lineChartLegend = true;
      this.lineChartPlugins = [];
      this.lineChartType = 'line';
    });
  
  }
  
   
}
