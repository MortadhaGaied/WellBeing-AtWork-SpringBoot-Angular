import { Component, Inject, OnInit } from '@angular/core';
import { Weather } from '../../Models/event/Weather';
import { EventService } from '../event-list/event.service';
import * as moment from 'moment';
import { ActivatedRoute } from '@angular/router';
import { Event } from '../../Models/event/Event';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-weather',
  templateUrl: './weather.component.html',
  styleUrls: ['./weather.component.css']
})

export class WeatherComponent implements OnInit {
  WeatherData:any;
  date = new Date("2022-05-05");
  currentdate=new Date();
  weather : Weather=new Weather();
  idEvent : number;
  event : Event =new Event();
  constructor(private service :EventService,private route:ActivatedRoute,private dialogRef:MatDialogRef<WeatherComponent>,@Inject(MAT_DIALOG_DATA) public data:any) { }

  ngOnInit(): void {
    this.idEvent=this.route.snapshot.params['id'];
console.log("****************************\n"+this.data.idev);
    this.service.getEventById(this.data.idev).subscribe((e)=>{
      this.event=e;
      console.log("EVEENT:"+this.event);
    });
    this.service.getWeather(this.data.idev).subscribe(w=>{
      this.weather=w;
      console.log(this.weather);
      this.date=w.forecastTime;
   
    });
    this.WeatherData = {
      main : {},
      isDay: true
    };
    this.getWeatherData();
    console.log(this.WeatherData);
    
  }

  getWeatherData(){
   

    let data = JSON.parse('{"coord":{"lon":72.85,"lat":19.01},"weather":[{"id":721,"main":"Haze","description":"haze","icon":"50n"}],"base":"stations","main":{"temp":297.15,"feels_like":297.4,"temp_min":297.15,"temp_max":297.15,"pressure":1013,"humidity":69},"visibility":3500,"wind":{"speed":3.6,"deg":300},"clouds":{"all":20},"dt":1580141589,"sys":{"type":1,"id":9052,"country":"IN","sunrise":1580089441,"sunset":1580129884},"timezone":19800,"id":1275339,"name":"Mumbai","cod":200}');
    this.setWeatherData(data);
  }

  setWeatherData(data:any){
    this.WeatherData = data;
    let sunsetTime = new Date(this.WeatherData.sys.sunset * 1000);
    this.WeatherData.sunset_time = sunsetTime.toLocaleTimeString();
    let currentDate = new Date();
    this.WeatherData.isDay = (currentDate.getTime() < sunsetTime.getTime());
    this.WeatherData.temp_celcius = (this.WeatherData.main.temp - 273.15).toFixed(0);
    this.WeatherData.temp_min = (this.WeatherData.main.temp_min - 273.15).toFixed(0);
    this.WeatherData.temp_max = (this.WeatherData.main.temp_max - 273.15).toFixed(0);
    this.WeatherData.temp_feels_like = (this.WeatherData.main.feels_like - 273.15).toFixed(0);
  }
  isDay(d:Date):boolean{
    return (moment(d).hours()<20 && moment(d).hours()>5);
  }
  close(){
    this.dialogRef.close();
   

  }
}