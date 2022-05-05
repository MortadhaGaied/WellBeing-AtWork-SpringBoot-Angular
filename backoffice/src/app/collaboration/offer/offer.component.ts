import { ShowOffersComponent } from './../show-offers/show-offers.component';
import { UpdateOfferComponent } from './../update-offer/update-offer.component';
import { OfferService } from './../../Service/offer.service';
import { Offer } from './../../Models/Collaboration/offer';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { MatDialog } from '@angular/material/dialog';

@Component({
  selector: 'app-offer',
  templateUrl: './offer.component.html',
  styleUrls: ['./offer.component.scss']
})
export class OfferComponent implements OnInit {
  o : Offer[];
  Offer : Offer;
  constructor(private OfferService: OfferService,private matDialog:MatDialog,private router : Router ) { }

  totalLentgh:any;
  page:number = 1;
  ngOnInit(): void {
    this.OfferService.getAllOffer().subscribe(
      (data)=>{ 
        console.log(data);
        this.o=data
        this.totalLentgh = data.length 
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

  deleteOffer(idOffer:number){
    this.OfferService.deleteOffer(idOffer)
    .subscribe(()=>this.OfferService.getAllOffer().subscribe(res=>{this.o=res}));
  }
  updateOffer(idCollaboration:number){
    
    this.Offer=this.OfferService.sendEventData(idCollaboration);
    this.matDialog.open(UpdateOfferComponent);
    }
    showOffer(Offer:Offer){
  
      this.matDialog.open(ShowOffersComponent ,{data :Offer});
      }
}
