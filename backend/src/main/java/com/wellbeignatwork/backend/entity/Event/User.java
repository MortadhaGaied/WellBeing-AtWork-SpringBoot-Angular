package com.wellbeignatwork.backend.entity.Event;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long idUser;
    private String firstName;
    private String lastName;
    private int points;
    @Enumerated(EnumType.STRING)
    private Departement departement;
    @Enumerated(EnumType.STRING)
    private UserType userType;
    @ElementCollection
    @CollectionTable(name = "userTags",joinColumns = @JoinColumn(name = "idUser"))
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Set<Tag> userTags;
    @ManyToMany
    @JsonIgnore
    private Set<Event> events;
    @OneToMany(mappedBy = "user")
    private Set<Subscription> subscriptions;
}
