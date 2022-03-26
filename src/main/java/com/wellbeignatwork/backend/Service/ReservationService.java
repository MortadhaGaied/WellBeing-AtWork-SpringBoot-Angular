package com.wellbeignatwork.backend.Service;


import com.lowagie.text.DocumentException;
import com.wellbeignatwork.backend.API.EmailSender;
import com.wellbeignatwork.backend.API.OfferPDFExporter;
import com.wellbeignatwork.backend.API.PdfAllOffre;
import com.wellbeignatwork.backend.API.ReservationPDFExporter;
import com.wellbeignatwork.backend.Repository.*;
import com.wellbeignatwork.backend.ServiceImp.IReservationService;
import com.wellbeignatwork.backend.model.Offer;
import com.wellbeignatwork.backend.model.Reservation;
import com.wellbeignatwork.backend.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
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
	EmailSender emailSender;




	@Override
	@Transactional
	public Reservation reservation(long idUser, long idOffer, Reservation r) {

		User u = userRepo.findById(idUser).orElse(null);
		Offer o = OfferRepo.findById(idOffer).orElse(null);
		if(o.getNplaces()<r.getNmPalce()) throw  new RuntimeException("Nombre de place insufosant");
		if(o.getStarDateOf().isAfter(r.getStartDateRes()))throw  new RuntimeException("Vous avez passer la date star") ;
		if(o.getEndDateOf().isBefore(r.getEndDateRes()))throw  new RuntimeException("Vous avez passer la date fin");
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

	@Scheduled(cron="*/30 * * * * *")
	public void findAllByStartDateResIsBefore() throws MessagingException {
		List<Reservation> reservationsWeek = reservationRepo.findAllByStartDateResIsBefore(LocalDateTime.now().plusDays(7));
		List<Reservation> reservationsDay = reservationRepo.findAllByStartDateResIsBefore(LocalDateTime.now().plusDays(1));
		for (Reservation r : reservationsWeek) {
			r.getUserRes().getEmail();
			List<Reservation> listReservations = listAll();
			ReservationPDFExporter exporter = new ReservationPDFExporter(listReservations);
			emailSender.sendMail("mahdi.homrani@esprit.tn"," add Reservartion " ," add succesful ... ");}

		}
	}

