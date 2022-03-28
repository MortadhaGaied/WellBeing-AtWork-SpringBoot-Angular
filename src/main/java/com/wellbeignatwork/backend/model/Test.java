package com.wellbeignatwork.backend.model;

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
@Table( name = "Courses")
public class Test {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer idTest;
    private String title;
    @Enumerated(EnumType.STRING)
    private Level level;
    @Temporal (TemporalType.TIMESTAMP)
    private Date start;
    @Temporal (TemporalType.TIMESTAMP)
    private Date end;
    private Integer nbrHeures;
    @Enumerated(EnumType.STRING)
    private Domain domain;

    private Integer nbrMaxParticipant;
    private Integer frais;

    private Integer likes;
    private Integer dislikes;

    @ManyToOne
    @JsonIgnore
    private User formateur;

    @ManyToMany
    @JsonIgnore
    private Set<User> apprenant ;

    @OneToMany(mappedBy = "test" ,cascade = {CascadeType.PERSIST,CascadeType.REMOVE})
    @JsonIgnore
    private Set<QuizCourses> quizzes;


    @OneToMany(mappedBy = "test",cascade = {CascadeType.PERSIST,CascadeType.REMOVE})
    @JsonIgnore
    private Set<DatabaseFile> databaseFiles;






}
