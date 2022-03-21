package com.wellbeignatwork.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    private Date sendAt = new Date();
    @JsonIgnore
    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.REMOVE})
    private User sender;
    @JsonIgnore
    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.REMOVE})
    private ChatRoom chatroom;

}
