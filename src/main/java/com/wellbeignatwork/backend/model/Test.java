package com.wellbeignatwork.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Set;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table( name = "Courses")
public class Test {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer idTest;
    private String title;
    @Enumerated(EnumType.STRING)
    private Level level;
    private Integer nbrHeures;
    @Enumerated(EnumType.STRING)
    private Domain domain;
    private Integer nbrParticipant;
    private Integer likes;
    private Integer dislikes;

    @ManyToMany
    @JsonIgnore
    private Set<User> employee ;

    @ManyToMany
    @JsonIgnore
    private Set<User> intern ;

    @OneToMany(mappedBy = "test" ,cascade = {CascadeType.PERSIST,CascadeType.REMOVE})
    @JsonIgnore
    private Set<QuizTheme> quizzes;


    @OneToMany(mappedBy = "test",cascade = {CascadeType.PERSIST,CascadeType.REMOVE})
    @JsonIgnore
    private Set<DatabaseFile> databaseFiles;






}
