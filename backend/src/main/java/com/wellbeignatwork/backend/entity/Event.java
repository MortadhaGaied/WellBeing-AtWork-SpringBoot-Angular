package com.wellbeignatwork.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class Event implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long idEvent;
    private String eventName;
    private String eventSeason;
    private String eventLocalisation;
    private int nbrMaxParticipant;
    private double frais;
    private double revenue;

    @Temporal(TemporalType.DATE)
    private Date eventDate;
    @ElementCollection
    @CollectionTable(name = "eventTags",joinColumns = @JoinColumn(name = "idEvent"))
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Set<Tag> eventTags;
    @ManyToMany(fetch = FetchType.LAZY ,mappedBy ="events")
    @JsonIgnore
    private Set<User> users;


}
