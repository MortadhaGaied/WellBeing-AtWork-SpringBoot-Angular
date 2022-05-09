import { Component, OnInit } from '@angular/core';







import{PointAndGiftsService} from '../point-and-gifts.service';
import { User } from '../user';

@Component({
  selector: 'app-points-and-gifts',
  templateUrl: './points-and-gifts.component.html',
  styleUrls: ['./points-and-gifts.component.scss']
})
export class PointsAndGiftsComponent implements OnInit {

  constructor(private PF:PointAndGiftsService) { }


  user:User[]

  ngOnInit(): void {

    this.PF.PointsRanking().subscribe(data=>{
      this.user=data;

    });



    }





}
