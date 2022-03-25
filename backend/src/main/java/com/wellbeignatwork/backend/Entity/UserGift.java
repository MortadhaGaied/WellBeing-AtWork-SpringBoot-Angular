package com.wellbeignatwork.backend.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserGift {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String code;

    private boolean validite;

    private int montant;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="idUser")
    private User idUser;
}
