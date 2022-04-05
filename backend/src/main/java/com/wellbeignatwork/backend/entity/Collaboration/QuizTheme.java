package com.wellbeignatwork.backend.entity.Collaboration;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class QuizTheme {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer idQuiz;

    private String title;

    private Integer score;


    private Date createAt;

    private String content ;

    @ManyToOne
    @JsonIgnore
    private Test test;

    @OneToMany(mappedBy = "quiz",cascade = {CascadeType.PERSIST,CascadeType.REMOVE})
    @JsonIgnore
    private Set<QuestionCollaboration> question;

    @OneToMany(mappedBy = "quiz",cascade = {CascadeType.PERSIST,CascadeType.REMOVE})
    @JsonIgnore
    private Set<Result> results;
}
