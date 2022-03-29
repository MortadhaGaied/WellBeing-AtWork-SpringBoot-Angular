package com.wellbeignatwork.backend.controller.Collaboration;

import com.sun.mail.iap.Response;

import com.wellbeignatwork.backend.entity.Collaboration.FeedBackCollaboration;
import com.wellbeignatwork.backend.entity.Collaboration.Offer;
import com.wellbeignatwork.backend.entity.Collaboration.OfferFeedbacks;
import com.wellbeignatwork.backend.service.Collaboration.IUsersOfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class UsersOfferController {
    @Autowired
    IUsersOfferService usersOfferService;

    @PutMapping("/add-offer-fav/{userId}")
    //@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public Response addFav(@RequestParam Long offerId,@PathVariable Long userId) {
        try {
            /*UserDetailsImpl currentUser = (UserDetailsImpl) authentication.getPrincipal();
            Long userId = currentUser.getId();*/
            usersOfferService.addOfferToFav(userId, offerId);
            return new Response("Offer is added successfully to your favorite list", true);
        } catch (Exception e) {
            return new Response(e.getMessage(), false);
        }
    }

    @GetMapping("/get-offers-fav/{userId}")
    //@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public List<Offer> getFav(@PathVariable Long userId) {
        /*UserDetailsImpl currentUser = (UserDetailsImpl) authentication.getPrincipal();
        Long userId = currentUser.getId();*/
        return usersOfferService.getFavOffer(userId);
    }

    @GetMapping("/get-invited-offers/{userId}")
    //@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public List<Offer> getInvited(@PathVariable Long userId) {
        /*UserDetailsImpl currentUser = (UserDetailsImpl) authentication.getPrincipal();
        Long userId = currentUser.getId();*/
        return usersOfferService.getInvitedOffers(userId);
    }

    @PutMapping("/feedback-offer/{offerId}/{userId}")
    //@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public Response feedbackOffer(@PathVariable Long offerId, @Valid @RequestBody FeedBackCollaboration feedback,@PathVariable Long userId) {
        try {
            /*UserDetailsImpl currentUser = (UserDetailsImpl) authentication.getPrincipal();
            Long userId = currentUser.getId();*/
            usersOfferService.feedbackOffer(userId, offerId, feedback);
            return new Response("Thank you for your feedback", true);
        } catch (Exception e) {
            return new Response(e.getMessage(), false);
        }
    }

    @GetMapping("/avg-rating/{offerId}")
    //@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public Object getAverageRatingOffer(@PathVariable Long offerId) {
        Map<String,Float> map=new HashMap<>();
        try {
            return map.put("average", usersOfferService.getAverageRateOffer(offerId));
        } catch (Exception e) {
            return new Response(e.getMessage(), false);
        }
    }

    @GetMapping("/get-offer-feedbacks/{offerId}")
    //@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public List<OfferFeedbacks> getOfferFeedbacks(@PathVariable Long offerId) {
        return usersOfferService.getFeedbackOffer(offerId);
    }
}
