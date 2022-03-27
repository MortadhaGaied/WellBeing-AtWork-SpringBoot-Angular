package com.wellbeignatwork.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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


    @JsonIgnore
    @ManyToOne
    Offer offersRes;
    @JsonIgnore
    @ManyToOne
    User userRes;

}