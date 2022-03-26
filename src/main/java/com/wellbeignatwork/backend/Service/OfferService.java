package com.wellbeignatwork.backend.Service;

import com.wellbeignatwork.backend.Repository.ICollaboration;
import com.wellbeignatwork.backend.Repository.IOffer;
import com.wellbeignatwork.backend.Repository.IPublicity;
import com.wellbeignatwork.backend.ServiceImp.IOfferService;
import com.wellbeignatwork.backend.model.Collaboration;
import com.wellbeignatwork.backend.model.Happy;
import com.wellbeignatwork.backend.model.Offer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;


@Service
public class OfferService implements IOfferService {
	@Autowired
	IOffer OfferRepo;

	@Autowired
	ICollaboration CollaborationRepo;

	@Autowired
	IPublicity PublicityRepo;


	@Override
	public List<Offer> retrieveAllOffers() {
		return OfferRepo.findAll();

	}

	@Override
	public void addOffer(Offer o, long idCollaboration) {
		Collaboration collaboration = CollaborationRepo.findById(idCollaboration).get();
		o.setCollaboration(collaboration);
		OfferRepo.save(o);
	}

	@Override
	public void deleteOffer(Long id) {
		OfferRepo.deleteById(id);
	}

	@Override
	public Offer updateOffer(Offer o) {
		return OfferRepo.save(o);
	}

	@Override
	public Offer retrieveOffer(Long id) {
		return OfferRepo.findById(id).orElse(null);

	}


	@Override
	@Transactional
	public float calculProm(long idOffer) {
		Offer o = OfferRepo.findById(idOffer).orElse(null);

			if (o.getHappy() == Happy.BLACK_FRIDAY) {
				o.setPromotion((o.getPrix() * 50) / 100);
			} else if (o.getHappy() == Happy.HAPPY_DAYS) {
				o.setPromotion((o.getPrix() * 20) / 100);
			} else if (o.getHappy() == Happy.HAPPY_HOUR) {
				o.setPromotion((o.getPrix() * 20) / 100);
			} else if(o.getHappy() == Happy.PROMOTION) {
				o.setPromotion((o.getPercentage() * o.getPrix()) / 100);
			}
			OfferRepo.save(o);
			return o.getPromotion();
	}
}


