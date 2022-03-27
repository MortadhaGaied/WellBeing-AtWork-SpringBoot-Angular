package com.wellbeignatwork.backend.Service;

import com.wellbeignatwork.backend.Repository.OfferRepository;
import com.wellbeignatwork.backend.Repository.UserRepository;
import com.wellbeignatwork.backend.Repository.UsersOfferRepo;
import com.wellbeignatwork.backend.ServiceImp.IUsersOfferService;
import com.wellbeignatwork.backend.exception.BadRequestException;
import com.wellbeignatwork.backend.exception.ResourceNotFoundException;
import com.wellbeignatwork.backend.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class UsersOfferService implements IUsersOfferService {

    @Autowired
    UserRepository userRepo;

    @Autowired
    OfferRepository offerRepo;

    @Autowired
    UsersOfferRepo usersOfferRepo;

    @Override
    public void addOfferToFav(Long userId, Long offerId) {
        User user = userRepo.findById(userId).orElse(null);
        Offer offer = offerRepo.findById(offerId).orElse(null);
        if (offer == null) {
            throw new ResourceNotFoundException("Event doesn't exist");
        }
        if (user != null) {
            UsersOffer usersOffer = getOrCreateUsersOffer(userId, offerId);
            usersOffer.setOffer(offer);
            usersOffer.setUser(user);
            usersOffer.setIsFavorite(true);
            usersOfferRepo.save(usersOffer );
        }
    }

    @Override
    public List<Offer> getFavOffer(Long userId) {
        List<UsersOffer> usersOffers = usersOfferRepo.findByUserIdAndIsFavoriteTrue(userId);
        List<Offer> offers = new ArrayList<>();
        usersOffers.forEach(uo -> offers.add(uo.getOffer()));
        return offers;
    }

    @Override
    public List<Offer> getInvitedOffers(Long userId) {
        List<UsersOffer> usersOffers = usersOfferRepo.findByUserIdAndIsInvitedTrue(userId);
        List<Offer> offers = new ArrayList<>();
        usersOffers.forEach(uo -> offers.add(uo.getOffer()));
        return offers;
    }

    @Override
    public List<Offer> getAcceptedOffers(Long userId) {
        List<UsersOffer> usersEvents = usersOfferRepo.findByUserIdAndIsAcceptedTrue(userId);
        List<Offer> offers = new ArrayList<>();
        usersEvents.forEach(uo -> offers.add(uo.getOffer()));
        return offers;
    }

    @Override
    public void acceptOffer(Long userId, Long offerId) {
        UsersOffer usersOffers = usersOfferRepo.findByUserIdAndOfferIdOffer(userId, offerId);
        if (usersOffers == null) {
            throw new BadRequestException("Cannot accept an event without invitation");
        }
        if (usersOffers.getIsAccepted()) {
            throw new BadRequestException("Your already accepted the invitation");
        }
        /*if (usersEvents.getEvent().getFees() > 0) {
            throw new BadRequestException("You must pay event fees first");
        }*/
        if (usersOffers.getIsInvited()) {
            usersOffers.setIsAccepted(true);
            usersOfferRepo.save(usersOffers);
        } else {
            throw new BadRequestException("Cannot accept an event without invitation");
        }
    }

    @Override
    public void feedbackOffer(Long userId, Long offerId, Feedback feedback) {
        UsersOffer usersOffer = usersOfferRepo.findByUserIdAndOfferIdOffer(userId, offerId);
        if (usersOffer == null) {
            throw new BadRequestException("Cannot give feedback for an event without participation");
        }
        LocalDateTime eventEndDate = usersOffer.getOffer().getEndDateOf();
        LocalDateTime now = LocalDateTime.now();
        if (!usersOffer.getIsAccepted()) {
            throw new BadRequestException("Cannot give feedback for an event without participation");
        }
        if (!eventEndDate.isBefore(now)) {
            throw new BadRequestException("Cannot give feedback for an event before its finish");
        }
        usersOffer.setFeedback(feedback.getContent());
        usersOffer.setRate(feedback.getRate());
        usersOfferRepo.save(usersOffer);
    }

    @Override
    public Float getAverageRateOffer(Long offerId) {
        if (!offerRepo.existsById(offerId)) {
            throw new ResourceNotFoundException("Event doesn't exist");
        }
        return usersOfferRepo.getAverageRateOffer(offerId);
    }

    @Override
    public List<OfferFeedbacks> getFeedbackOffer(Long eventId) {
        if (!offerRepo.existsById(eventId)) {
            throw new ResourceNotFoundException("Event doesn't exist");
        }
        List<UsersOffer> usersOffers = usersOfferRepo.findByOfferIdOfferAndAndIsAcceptedTrue(eventId);
        List<OfferFeedbacks> feedbacks = new ArrayList<>();
        for (UsersOffer ue : usersOffers) {
            OfferFeedbacks res = new OfferFeedbacks(ue.getUser().getId(), ue.getUser().getName());
            res.setRate(ue.getRate());
            res.setContent(ue.getFeedback());
            feedbacks.add(res);
        }
        return feedbacks;
    }

    public UsersOffer getOrCreateUsersOffer(Long userId, Long offerId) {
        UsersOffer usersOffer= usersOfferRepo.findByUserIdAndOfferIdOffer(userId, offerId);
        if (usersOffer != null) {
            return usersOffer;
        }
        usersOffer = new UsersOffer();
        usersOffer.setId(new UsersOfferKey(userId, offerId));
        return usersOffer;
    }
}
