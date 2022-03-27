package com.wellbeignatwork.backend.Repository;

import com.wellbeignatwork.backend.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;


@Repository
public interface IReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findAllByStartDateResIsBefore(LocalDateTime date);

}
