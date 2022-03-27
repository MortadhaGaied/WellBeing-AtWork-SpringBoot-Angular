package com.wellbeignatwork.backend.Controller;


import com.stripe.exception.StripeException;
import com.wellbeignatwork.backend.API.StripeService;
import com.wellbeignatwork.backend.Repository.UserRepository;
import com.wellbeignatwork.backend.ServiceImp.IReservationService;
import com.wellbeignatwork.backend.model.Payment;
import com.wellbeignatwork.backend.model.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;

@RequestMapping("/Reservation")
@RestController
public class ReservationController {

    @Autowired
    IReservationService reservationService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    StripeService stripeService;

    //http://localhost:8080/Reservation/addResevation/1/1
    //local date time 2022-03-24T12:59:11.332
    @PostMapping("/addResevation/{idOffer}/{idUser}")
    @ResponseBody
    public void addOffer(@RequestBody Reservation r, @PathVariable long idUser, @PathVariable long idOffer) throws MessagingException {
        reservationService.reservation(idUser,idOffer,r);
    }

    //http://localhost:8080/Reservation/calculTotal/1/1
    @GetMapping("/calculTotal/{idOffer}/{idReservation}")
    @ResponseBody
    public float calculTotal( @PathVariable long idReservation, @PathVariable long idOffer){
        return reservationService.prixTotale(idReservation,idOffer);
    }

    //http://localhost:8080/Reservation/stripe/1/1
    @PostMapping("/stripe/{idUser}/{idReservation}")
    @ResponseBody
    public Payment index(@PathVariable long idUser ,@PathVariable long idReservation , @RequestBody Payment p ) throws StripeException {
        return stripeService.payment(idUser,idReservation,p);
    }

    @PostMapping("/stripe/{email}/{token}/{idUser}/{idReservation}/{idOffer}")
    @ResponseBody
    public Reservation createCharge(@PathVariable String email,@PathVariable String token,@PathVariable Long idUser,@PathVariable Long idReservation,@PathVariable Long idOffer,@RequestBody Reservation r) throws StripeException, MessagingException {
        return stripeService.createCharge(email,token,idUser,idReservation,idOffer,r);
    }
}
