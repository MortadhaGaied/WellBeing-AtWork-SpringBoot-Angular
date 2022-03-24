package com.wellbeignatwork.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userName;

    private String fireBaseToken;
    @JsonIgnore
    @ManyToMany(mappedBy = "users",fetch = FetchType.EAGER)
    private Set<ChatRoom> rooms;
    @JsonIgnore
    @OneToMany(mappedBy = "sender",fetch = FetchType.EAGER)
    private Set<Message>messages;


}
