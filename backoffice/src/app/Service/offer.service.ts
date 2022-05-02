import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Offer } from '../Models/Collaboration/offer';
import { Observable } from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class OfferService {

  constructor(private http :HttpClient) { }

  getAllOffer():Observable<Offer[]>{
    return this.http.get<Offer[]>("http://localhost:8081/Offer/retrieveAllOffers/")
  }
  getOfferById( idOffer : number){
    return this.http.get("http://localhost:8081/Offer/retrieveOffer/"+idOffer)
  }
  updateOffer(offer: Offer){
    return this.http.put("http://localhost:8081/Offer/updateOffer/",offer)
  }

  addOffer(idOffer : number,offer :Offer){
    return this.http.post("http://localhost:8081/Offer/addOffer/"+idOffer,offer)
  }

  deleteOffer(idOffer: number){
    return this.http.delete("http://localhost:8081/Offer/deleteOffer/"+idOffer)
  }
}
