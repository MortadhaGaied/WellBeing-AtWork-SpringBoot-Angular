import { Component, OnInit } from '@angular/core';
import { Post } from '../../../Models/Forum/Post';
import { ChartDataSets, ChartOptions } from 'chart.js';
import { Color, Label } from 'ng2-charts';
import { BlogsServiceService } from '../blogs-service.service';
@Component({
  selector: 'app-blogs-stats',
  templateUrl: './blogs-stats.component.html',
  styleUrls: ['./blogs-stats.component.scss']
})
export class BlogsStatsComponent implements OnInit {

  constructor(private _service:BlogsServiceService) { }

  listPost : Post[];
  updateId : number;
  lineChartData: ChartDataSets[]=[];
  lineChartLabels: Label[] =[];
  lineChartColors: Color[] =[];
  lineChartPlugins = [];
  lineChartLegend:boolean=false;
  lineChartType:string='';
  lineChartOptions:any;
  lineChartData2: ChartDataSets[]=[];
  lineChartLabels2: Label[] =[];
  lineChartColors2: Color[] =[];
  lineChartPlugins2 = [];
  lineChartLegend2:boolean=false;
  lineChartType2:string='';
  lineChartOptions2:any;
  satisfaction:number[]=[];
  postssubject:string[]=[];
  ngOnInit(): void {
    this._service.getAllPost().subscribe(res=>{
      res.forEach(
        p=>{
          this.postssubject.push(p.subject);
        }
      )
    })
    this._service.getAllPost().subscribe(res=>{
      res.forEach(p=>{
        this._service.getpostsatisfaction(p.id).subscribe(r=>{
          this.satisfaction.push(r);
        });
        
      });
      this.lineChartData2=[
        {
          data:this.satisfaction,label:'Satisfaction'
        },
      ];
      this.lineChartLabels2=this.postssubject;
      this.lineChartOptions2 = {
        responsive: true,
      };
     
      this.lineChartColors2= [
        {
          borderColor: 'black',
          backgroundColor: 'rgba(51, 122, 255)',
        },
      ];
     
      this.lineChartLegend2 = true;
      this.lineChartPlugins2 = [];
      this.lineChartType2 = 'line';
      

    })
    this._service.getPostsInteraction().subscribe(res=>{
      console.log(res);
      
      
      this.lineChartData = [
        { 
          data: res, label: 'Interaction' 
        },
      ];
     
      this.lineChartLabels = this.postssubject;
     
      this.lineChartOptions = {
        responsive: true,
      };
     
      this.lineChartColors= [
        {
          borderColor: 'black',
          backgroundColor: 'rgba(51, 122, 255)',
        },
      ];
     
      this.lineChartLegend = true;
      this.lineChartPlugins = [];
      this.lineChartType = 'line';
    });
  
  }
  
   
}
