package com.wellbeignatwork.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class ChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String roomName;
    //this key helps creating unique rooms for one to one chatting
    private String uniqueKey;

    private String averageResponseTime;


    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<User> users;
    @JsonIgnore
    @OneToMany(mappedBy = "chatroom", fetch = FetchType.EAGER)
    private List<Message> messages;



}
