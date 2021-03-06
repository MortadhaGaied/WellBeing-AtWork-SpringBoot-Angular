package com.wellbeignatwork.backend.entity.Chat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wellbeignatwork.backend.entity.User.User;
import lombok.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.Date;
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
    private int MaxBadWords=10;
    private int capacity;
    private boolean isVisible;
    @Enumerated(EnumType.STRING)
    private RoomStatus status;

    private String averageResponseTime;
    private String image;
    private int ownerId;
    @Temporal(TemporalType.DATE)
    private Date creationDate;
    @LazyCollection(LazyCollectionOption.FALSE)
    @ElementCollection
    private List<Long> bannList;


    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<User> users;
    @JsonIgnore
    @OneToMany(mappedBy = "chatroom", fetch = FetchType.EAGER)
    private List<Message> messages;



}
