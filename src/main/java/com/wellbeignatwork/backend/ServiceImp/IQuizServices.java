package com.wellbeignatwork.backend.ServiceImp;

import com.wellbeignatwork.backend.model.Question;
import com.wellbeignatwork.backend.model.Quiz;

import java.util.List;

public interface IQuizServices {
	public List<Quiz> getQuizes();

	Quiz addQuiz(Quiz quiz);

	public void deleteQuiz(int id);
	public void updateQuiz(Quiz quiz);
	
	public List<Question> getQuestions(int id);
	public void addQuestion(Question ques);
	public void deleteQues(int id, int quizID);
	public void updateQues(Question ques);
	 
}
