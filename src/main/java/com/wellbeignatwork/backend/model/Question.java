package com.wellbeignatwork.backend.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
@Entity
public class Question implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
    private String question;
    private String answer;
    private int marks;

	@ManyToOne
    private Quiz quiz;
    
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;
    

}
