package com.wellbeignatwork.backend.Entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idComment;
    private String commentContent;
    private LocalDateTime createDate;
    private LocalDateTime modifyDate;
    private int idUser;
    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Post post;
}
