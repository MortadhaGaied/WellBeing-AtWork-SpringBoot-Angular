package com.wellbeignatwork.backend.Service;


import com.wellbeignatwork.backend.API.EmailSender;
import com.wellbeignatwork.backend.Repository.*;
import com.wellbeignatwork.backend.ServiceImp.IReservationService;
import com.wellbeignatwork.backend.model.Offer;
import com.wellbeignatwork.backend.model.Reservation;
import com.wellbeignatwork.backend.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class ReservationService implements IReservationService {
	@Autowired
	IOffer OfferRepo;

	@Autowired
	ICollaboration CollaborationRepo;

	@Autowired
	IPublicity PublicityRepo;
	
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	IReservation reservationRepo;

	@Autowired
	private EmailSender emailSender;
	


	@Override
	@Transactional
	public Reservation reservation(long idUser, long idOffer, Reservation r) {

		User u = userRepo.findById(idUser).orElse(null);
		Offer o = OfferRepo.findById(idOffer).orElse(null);
		if(o.getNplaces()<r.getNmPalce()) throw  new RuntimeException("Nombre de place insufosant");
		if(o.getStarDateOf().after(r.getStartDateRes()))throw  new RuntimeException("Vous avez passer la date star") ;
		if(o.getEndDateOf().before(r.getEndDateRes()))throw  new RuntimeException("Vous avez passer la date fin");
		r.setUserRes(u);
		r.setOffersRes(o);
		reservationRepo.save(r);
		o.setNplaces(o.getNplaces()-r.getNmPalce());
		return r;
	}

	@Override
	public float prixTotale(long idOffer, long idReservation) {
		Reservation r = reservationRepo.findById(idReservation).get();
		Offer o = OfferRepo.findById(idOffer).get();
		float total=0;
		total = r.getNmPalce() * o.getPromotion();
		return total;
	}

	@Override
	public Reservation findById(long idReservation) {
		return reservationRepo.findById(idReservation).orElse(null);
	}

	@Override
	public List<Reservation> findAll() {
		return  reservationRepo.findAll();
	}

	@Override
	public List<Reservation> listAll() {
		return reservationRepo.findAll(Sort.by("idReservation").ascending());
	}

}
