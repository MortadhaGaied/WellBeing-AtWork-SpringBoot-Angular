package com.wellbeignatwork.backend.ServiceImp;


import com.wellbeignatwork.backend.model.Reservation;

import java.util.List;

public interface IReservationService {

	Reservation reservation(long idUser, long idOffer, Reservation r);

	float prixTotale(long idUser, long idReservation);

	Reservation findById(long idReservation);

	List<Reservation> findAll();
}