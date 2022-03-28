package com.wellbeignatwork.backend.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
@Entity
public class Quiz implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idQuiz;
    private String title;
    private String description;

	@OneToMany(cascade = CascadeType.REMOVE, mappedBy = "quiz")
    private List<Question> questions;
    

}
