package com.wellbeignatwork.backend.entity.Evaluation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import com.wellbeignatwork.backend.entity.User;
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
