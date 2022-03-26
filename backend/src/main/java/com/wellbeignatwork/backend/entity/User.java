package com.wellbeignatwork.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    private int idUser;
    @Enumerated(EnumType.STRING)
    private Role role;
    private String login;
    private String FirstName;
    private String LastName;
    private int pointFidelite;
    @OneToMany(mappedBy = "usersEvents")
    @JsonIgnore
    private Set<Events> Events;
    @OneToMany(mappedBy = "usersComments")
    @JsonIgnore
    private Set<Comment> Comments;
    @OneToMany(mappedBy = "usersLikes")
    @JsonIgnore
    private Set<Likes> Likes;

    @JsonIgnore
    @OneToMany(mappedBy = "idUser")
    private Set<UserGift> CadeauUser;

    @Enumerated(EnumType.STRING)
    private Badge badge;








}
