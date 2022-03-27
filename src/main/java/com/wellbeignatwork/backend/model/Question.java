package com.wellbeignatwork.backend.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Questions")
public class Question{
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long questionId;
	
	@Column(name = "question_title", nullable = false)
	private String title;
	
	@OneToMany(targetEntity= Theme.class, mappedBy="question", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<Theme> themes; //value to text
	
	@ManyToOne()
	@JoinColumn(name="quiz_id")
	private Quiz quiz;

	private String themeChosen =null;

}
