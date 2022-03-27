package com.wellbeignatwork.backend.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "options")
public class Theme {
  
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long Id;
	

	private String optionText;
	
	@ManyToOne()
	@JoinColumn(name="question_id")
	private Question question;

	public Theme() {

	}
	public Theme(String optionText) {
		super();
		this.optionText = optionText;
	}


}
