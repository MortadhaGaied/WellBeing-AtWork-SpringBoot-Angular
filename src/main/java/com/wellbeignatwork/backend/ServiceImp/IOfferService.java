package com.wellbeignatwork.backend.ServiceImp;


import com.wellbeignatwork.backend.model.Offer;

import java.util.List;

public interface IOfferService {
    List<Offer> retrieveAllOffers();

    void addOffer(Offer o,long idCollaboration);

    void deleteOffer(Long id);

    Offer updateOffer(Offer o);

    Offer retrieveOffer(Long id);

    List<Offer> listAll() ;
    
    float calculProm(long idOffer);


}
