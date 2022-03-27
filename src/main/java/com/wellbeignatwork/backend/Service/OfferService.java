package com.wellbeignatwork.backend.Service;

import com.github.prominence.openweathermap.api.model.onecall.current.CurrentWeatherData;
import com.sun.mail.iap.Response;
import com.wellbeignatwork.backend.API.WeatherService;
import com.wellbeignatwork.backend.Repository.ICollaboration;
import com.wellbeignatwork.backend.Repository.IOffer;
import com.wellbeignatwork.backend.Repository.IPublicity;
import com.wellbeignatwork.backend.ServiceImp.IOfferService;
import com.wellbeignatwork.backend.exception.ResourceNotFoundException;
import com.wellbeignatwork.backend.model.Collaboration;
import com.wellbeignatwork.backend.model.Happy;
import com.wellbeignatwork.backend.model.Offer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;


@Service
public class OfferService implements IOfferService {
	@Autowired
	IOffer OfferRepo;

	@Autowired
	ICollaboration CollaborationRepo;

	@Autowired
	IPublicity PublicityRepo;

	@Autowired
	WeatherService weatherService;

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
	public List<Offer> listAll() {
		return OfferRepo.findAll(Sort.by("idOffer").ascending());
	}

	@Override
	public Object getOfferWeather(Long idOffer) {
		Offer offer = OfferRepo.findById(idOffer).orElse(null);
		if (offer == null) {
			throw new ResourceNotFoundException("Event is not exist");
		}
		//check if the event is already started
		if (LocalDate.now().isAfter(offer.getStarDateOf().toLocalDate())) {
			return new Response("No need to fetch weather of an event already started or finished", false);
		}
		CurrentWeatherData currentWeatherData = weatherService.getWeatherData(offer.getLocalisation());
		LocalDate nextWeek = LocalDate.now().plusDays(7);
		System.out.println(nextWeek);
		LocalDate offerStartDate = offer.getStarDateOf().toLocalDate();
		if (nextWeek.isBefore(offerStartDate) || nextWeek.isEqual(offerStartDate)) {
			return currentWeatherData.getCurrent();
		} else {
			int idx = 7 - Period.between(offerStartDate, nextWeek).getDays();
			return currentWeatherData.getDailyList().get(idx);
		}
	}
}


