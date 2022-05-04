import { CollaborationService } from './../../Service/collaboration.service';
import { Collaboration } from './../../Models/Collaboration/collaboration';
import { Component, OnInit } from '@angular/core';
import { OfferService } from '../../Service/offer.service';
import { Offer } from '../../Models/Collaboration/offer';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';



@Component({
  selector: 'app-add-offer',
  templateUrl: './add-offer.component.html',
  styleUrls: ['./add-offer.component.scss']
})
export class AddOfferComponent implements OnInit {
  idCollaboration: any;
  offer: Offer = {
    idOffer: 0,
    title: '',
    descrption: '',
    starDateOf: new Date(),
    endDateOf: new Date(),
    nplaces: 0,
    promotion: 0,
    percentage: 0,
    location: '',
    prix: 0,
    rate: '',
    picture: {
      id: 0,
      name: ''
    },
    Happy: '',
    collaboration: new Collaboration
  };
  file: File;
  o: Offer[];

  title: any;
  button: any;
  idOffer: any;
  formOffer: FormGroup;

  constructor(private formBuilder: FormBuilder, private ActivatedRoute: ActivatedRoute, router: Router,
    private OfferService: OfferService, private CollaborationService: CollaborationService) { }
  collaborations: Collaboration[] = [];
  selectedcollaborationId: any;
  ngOnInit(): void {
    this.CollaborationService.getAllColaboration().subscribe(collaborations => {
      this.collaborations = collaborations
      console.log(collaborations)
    })

  }
  onSelectCollaboration(event: any) {
    this.selectedcollaborationId = event.target.value;
    //console.log(this.selectedcollaboration);
    console.log(event.target.value)
  }

  onFileSelected(event:any){
    this.file=event.target.files[0];
    console.log(this.file)
  }
  addOffer() {
    console.log(this.file);
    const formdata = new FormData();
    formdata.append('image', this.file, this.file.name);
    this.OfferService.addOffer(this.selectedcollaborationId, this.offer).subscribe(offer => {
      console.log(offer)
      this.OfferService.uploadImageToOffer(formdata,JSON.parse(JSON.stringify(offer)).idCollaboration).subscribe(data => window.alert("image uploaded successfully"),(error)=>console.log(error))})
  }
}
