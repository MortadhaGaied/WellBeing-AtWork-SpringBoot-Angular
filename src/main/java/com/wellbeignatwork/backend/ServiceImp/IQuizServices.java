package com.wellbeignatwork.backend.ServiceImp;

import com.wellbeignatwork.backend.model.Question;
import com.wellbeignatwork.backend.model.Quiz;

import java.util.List;

public interface IQuizServices {
	public List<Quiz> getQuizes();

	Quiz addQuiz(Quiz quiz);

	public void deleteQuiz(Long id);
	Quiz updateQuiz(Quiz quiz,Long id);
	
	public List<Question> getQuestions(Long id);
	public void addQuestion(Question ques);
	public void deleteQues(Long id, Long quizID);
	public void updateQues(Question ques);
	 
}
