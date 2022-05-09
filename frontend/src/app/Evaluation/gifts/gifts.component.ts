import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { EvaluationService } from '../evaluation.service';
import { User } from '../User';
import { UserGift } from '../UserGift';

@Component({
  selector: 'app-gifts',
  templateUrl: './gifts.component.html',
  styleUrls: ['./gifts.component.css']
})
export class GiftsComponent implements OnInit {

  constructor(private evaluation:EvaluationService,private route: ActivatedRoute) { }

  idUser:number

  gifts:UserGift;

  gift:number



  ngOnInit(): void {

    }




    UserGift(idUser)
  {

    this.evaluation.UserGift(this.idUser).subscribe(data=>{
      this.gifts=data;
      console.log(data)
      
    });

  }









}
