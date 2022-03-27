package com.wellbeignatwork.backend.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
@Entity
public class Reservation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long idReservation;
    LocalDateTime startDateRes;
    LocalDateTime endDateRes;
    int nmPalce;
    int priceTotal;



    @ManyToOne
    Offer offersRes;
    
    @ManyToOne
    User userRes;




}