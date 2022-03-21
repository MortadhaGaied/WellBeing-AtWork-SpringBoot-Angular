package com.wellbeignatwork.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
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
    @JsonIgnore
    @ManyToMany(mappedBy = "users",cascade = CascadeType.ALL)
    private Set<ChatRoom> rooms=new HashSet<>();
    @OneToMany(mappedBy = "sender")
    private Set<Message>messages=new HashSet<>();


}
