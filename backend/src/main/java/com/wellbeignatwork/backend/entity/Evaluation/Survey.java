package com.wellbeignatwork.backend.entity.Evaluation;

import javax.persistence.Id;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wellbeignatwork.backend.entity.User.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Survey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private String description;
    @OneToMany(mappedBy = "survey")
    @JsonIgnore
    private List<Question> questions;

    @ManyToMany(fetch = FetchType.LAZY ,mappedBy ="survey")
    @JsonIgnore
    private Set<User> users;


    public Survey(String title, String description, List<Question> questions) {
        this.title = title;
        this.description = description;
        this.questions = questions;
    }

}
