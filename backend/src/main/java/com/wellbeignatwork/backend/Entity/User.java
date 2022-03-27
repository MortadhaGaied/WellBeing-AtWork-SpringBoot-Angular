package com.wellbeignatwork.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wellbeignatwork.backend.entity.Evaluation.Badge;
import com.wellbeignatwork.backend.entity.Evaluation.UserGift;
import com.wellbeignatwork.backend.entity.Event.*;
import com.wellbeignatwork.backend.entity.Forum.Post;
import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Set;
@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private int pointFidelite;
    @Enumerated(EnumType.STRING)
    private Departement departement;

    @ElementCollection
    @CollectionTable(name = "userTags",joinColumns = @JoinColumn(name = "idUser"))
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Set<Tags> userTags;
    @ManyToMany
    @JsonIgnore
    private Set<Event> events;
    @OneToMany(mappedBy = "user")
    private Set<Subscription> subscriptions;
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Post> posts;
    @JsonIgnore
    @OneToMany(mappedBy = "idUser")
    private Set<UserGift> CadeauUser;

    @Enumerated(EnumType.STRING)
    private Badge badge;

}
