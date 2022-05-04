import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Offer } from '../../Models/Collaboration/offer';
import { OfferService } from '../../Service/offer.service';

@Component({
  selector: 'app-update-offer',
  templateUrl: './update-offer.component.html',
  styleUrls: ['./update-offer.component.scss']
})
export class UpdateOfferComponent implements OnInit {

  file:File;
  Offre : Offer = new Offer;
  title: any;
  button: any;
  idOffre: any;
  formdata : FormData = new FormData();
  formOffer: FormGroup;

  constructor(private formBuilder: FormBuilder, private activatedRoute: ActivatedRoute, router: Router,@Inject(OfferService)
    private OfferService: OfferService) { }

  ngOnInit(): void {
    this.OfferService.$eventEmit.subscribe((data)=> {
      this.Offre=data;
      console.log(this.Offre);
    })
  }
  onFileSelected(event:any){
    this.file=event.target.files[0];
    console.log(this.file)
  }
  updateOffer(){
    console.log(this.file);
    console.log(this.Offre)
    this.Offre.imagesOffer=undefined;
    const formdata=new FormData();
  formdata.append('image',this.file,this.file.name);
console.log(formdata.get('image'))
this.OfferService.updateOffer(this.Offre).subscribe(o => {
  console.log(o)
  this.OfferService.uploadImageToOffer(this.formdata,JSON.parse(JSON.stringify(o)).idOffer).subscribe(data => window.alert("image uploaded successfully"),(error)=>console.log(error))})
  
  }

}
