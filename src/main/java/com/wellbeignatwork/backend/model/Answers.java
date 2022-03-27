package com.wellbeignatwork.backend.model;


public class Answers {

	String[] answers = new String[20]; 
	
	public Answers() {
		
	}
	

	public Answers(String[] answers) {
		super();
		this.answers = answers;
	}


	public String[] getAnswers() {
		return answers;
	}

	public void setAnswers(String[] answers) {
		this.answers = answers;
	}
	
	
}
