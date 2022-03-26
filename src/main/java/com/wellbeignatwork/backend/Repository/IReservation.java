package com.wellbeignatwork.backend.Repository;


import com.wellbeignatwork.backend.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;





@Repository
public interface IReservation  extends JpaRepository<Reservation, Long> {

    @Override
    Optional<Reservation> findById(Long idReservation);

    @Override
    List<Reservation> findAll();
}
