import { OfferService } from './../../Service/offer.service';
import { Offer } from './../../Models/Collaboration/offer';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-offer',
  templateUrl: './offer.component.html',
  styleUrls: ['./offer.component.scss']
})
export class OfferComponent implements OnInit {
  o : Offer[];
  constructor(private OfferService: OfferService,private router : Router ) { }

  ngOnInit(): void {
    this.OfferService.getAllOffer().subscribe(
      (data)=>{ 
        console.log(data);
        this.o=data
      });
  }

  viewCollaboration(idOffer:number){
    let collaboration;
    for (let i=0;i<this.o.length;i++) {
      if (this.o[i].idOffer == idOffer){
        collaboration= this.o[i];
      }
      this.router.navigate([`viewOffer/${idOffer}`])
    }
  }
}
