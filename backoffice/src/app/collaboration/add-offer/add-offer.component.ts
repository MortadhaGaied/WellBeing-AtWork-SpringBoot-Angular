import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { OfferService } from '../../Service/offer.service';
import { Offer } from '../../Models/Collaboration/offer';

@Component({
  selector: 'app-add-offer',
  templateUrl: './add-offer.component.html',
  styleUrls: ['./add-offer.component.scss']
})
export class AddOfferComponent implements OnInit {
  idCollaboration: any;
  offer : any;
  o : Offer[];
  
  title:any;
  button: any;
  idOffer:any;
  formOffer : FormGroup;

  constructor(private formBuilder : FormBuilder ,private ActivatedRoute : ActivatedRoute , router : Router, 
    private OfferService : OfferService) { }

  ngOnInit(): void {
  }

  AddOrEditOffer(){

    this.idCollaboration = this.ActivatedRoute.snapshot.paramMap.get('idCollaboration');
      this.OfferService.addOffer(this.offer,this.idCollaboration).subscribe(
        (data)=>{
          console.log(data)
        }
        );
    }
}
