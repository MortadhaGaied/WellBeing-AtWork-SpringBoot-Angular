package com.wellbeignatwork.backend.entity.Forum;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Reaction implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idReaction;
    @Enumerated(EnumType.STRING)
    private ReactionType reactionType;
    private Long idUser;
    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Post post;

}
