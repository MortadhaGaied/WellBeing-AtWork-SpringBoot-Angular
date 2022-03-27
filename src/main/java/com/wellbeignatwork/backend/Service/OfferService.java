package com.wellbeignatwork.backend.Service;

import com.github.prominence.openweathermap.api.model.onecall.current.CurrentWeatherData;
import com.sun.mail.iap.Response;
import com.wellbeignatwork.backend.API.WeatherService;
import com.wellbeignatwork.backend.Repository.*;
import com.wellbeignatwork.backend.ServiceImp.IOfferService;
import com.wellbeignatwork.backend.exception.BadRequestException;
import com.wellbeignatwork.backend.exception.ResourceNotFoundException;
import com.wellbeignatwork.backend.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;


@Service
public class OfferService implements IOfferService {
	@Autowired
    OfferRepository OfferRepo;

	@Autowired
	ICollaboration CollaborationRepo;

	@Autowired
	IPublicity PublicityRepo;
	@Autowired
	WeatherService weatherService;
	@Autowired
	UserRepository userRepository;
	@Autowired
	UsersOfferRepo usersOfferRepo;
	@Autowired
	MailService mailService;

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
	@Override
	public void inviteUsersToOffer(List<Long> usersId, Long offerId) {
		Offer offer = OfferRepo.findById(offerId).orElse(null);
        if (offer == null) {
		throw new ResourceNotFoundException("Event doesn't exist");
	}
        if (offer.getStarDateOf().isBefore(LocalDateTime.now())) {
		throw new BadRequestException("You can't invite someone to an event already started or finished");
	}
        for (Long userId : usersId) {
			User user = userRepository.findById(userId).orElse(null);
			if (user == null) break;
			boolean alreadyInvited = false;
			UsersOffer uo = usersOfferRepo.findByUserIdAndOfferIdOffer(userId, offerId);
			//Check if already this user has been invited
			if (uo != null) {
				if (!uo.getIsInvited()) {
					System.out.println(uo.getUser().getName() + " to invite");
					uo.setIsInvited(true);
					usersOfferRepo.save(uo);
				} else {
					alreadyInvited = true;
				}
			} else {
				UsersOffer usersOffer = new UsersOffer();
				usersOffer.setId(new UsersOfferKey(userId, offerId));
				usersOffer.setUser(user);
				usersOffer.setOffer(offer);
				usersOffer.setIsInvited(true);
				usersOfferRepo.save(usersOffer);
			}

			if (!alreadyInvited) {
				String inviteContent = "You have been invited to " +
						offer.getTitle() +
						".\n Venue : " +
						offer.getLocalisation() +
						"\n Start at : " +
						offer.getStarDateOf() +
						"\n Finish at : " +
						offer.getEndDateOf();

				//send mail and sms when inviting user
				mailService.sendMail(user.getEmail(), "You have been invited!", inviteContent, false);
				//smsService.sendSMS(inviteContent);
			}
		}
	}
}


